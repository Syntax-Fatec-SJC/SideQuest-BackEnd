package com.syntax.tarefas_service.seguranca;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que garante que requisições vêm do API Gateway e extrai informações do
 * usuário dos headers
 */
@Component
@Order(1)
public class GatewayAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);

    private static final String GATEWAY_SECRET_HEADER = "X-Gateway-Secret";
    private static final String GATEWAY_SECRET = "SideQuestGatewaySecret2024";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_EMAIL_HEADER = "X-User-Email";

    private static final String USER_NAME_HEADER = "X-User-Name";
    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        // Permite endpoints públicos (health, swagger)
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se vem do Gateway
        String gatewaySecret = httpRequest.getHeader(GATEWAY_SECRET_HEADER);

        if (gatewaySecret == null || !gatewaySecret.equals(GATEWAY_SECRET)) {
            logger.warn("❌ Tentativa de acesso direto bloqueada: {} - IP: {}",
                    path, httpRequest.getRemoteAddr());
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(
                    "{\"erro\":\"Acesso direto não permitido. Use o API Gateway (porta 8080).\"}"
            );
            return;
        }

        // Extrai informações do usuário dos headers
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        String userEmail = httpRequest.getHeader(USER_EMAIL_HEADER);

        String userName = httpRequest.getHeader(USER_NAME_HEADER);
        
        logger.info("🔍 DEBUG HEADERS - Path: {}, X-Gateway-Secret: {}, X-User-Id: {}, X-User-Email: {}, X-User-Name: {}", 
            path, (gatewaySecret != null ? "presente" : "ausente"), userId, userEmail, userName);
        
        if (userId == null || userEmail == null) {
            logger.warn("⚠️ Headers de autenticação ausentes: {} - userId: {}, userEmail: {}", path, userId, userEmail);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }

        // Adiciona ao request para uso nos controllers
        httpRequest.setAttribute("userId", userId);
        httpRequest.setAttribute("userEmail", userEmail);

        logger.debug("✅ Requisição autenticada via Gateway - User: {} ({})", userEmail, userId);

        httpRequest.setAttribute("userName", userName);
        
        logger.debug("✅ Requisição autenticada via Gateway - User: {} ({}) - Nome: {}", userEmail, userId, userName);
        
        chain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.contains("/actuator")
                || path.contains("/swagger")
                || path.contains("/api-docs")
                || path.contains("/v3/api-docs")
                || path.equals("/health");
    }
}
