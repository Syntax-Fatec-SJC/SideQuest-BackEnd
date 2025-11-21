package com.syntax.calendario_service.segurança;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class GatewayAuthenticationFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);
    
    private static final String GATEWAY_SECRET_HEADER = "X-Gateway-Secret";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_EMAIL_HEADER = "X-User-Email";

    @Value("${gateway.secret}")
    private String gatewaySecretValue;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI();
        
        // Permite endpoints públicos (health, swagger, e o callback do Google)
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Verifica se vem do Gateway
        String gatewaySecretHeader = httpRequest.getHeader(GATEWAY_SECRET_HEADER);
        
        if (gatewaySecretHeader == null || !gatewaySecretHeader.equals(gatewaySecretValue)) {
            logger.warn("❌ Tentativa de acesso direto bloqueada: {} - IP: {}", 
                path, httpRequest.getRemoteAddr());
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(
                "{\"erro\":\"Acesso direto não permitido. Use o API Gateway.\"}"
            );
            return;
        }
        
        // Extrai informações do usuário dos headers
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        String userEmail = httpRequest.getHeader(USER_EMAIL_HEADER);
        
        if (userId == null || userEmail == null) {
            logger.warn("⚠️ Headers de autenticação ausentes: {}", path);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }
        
        // Adiciona ao request para uso nos controllers
        httpRequest.setAttribute("userId", Long.parseLong(userId));
        httpRequest.setAttribute("userEmail", userEmail);
        
        logger.debug("✅ Requisição autenticada via Gateway - User: {} ({})", userEmail, userId);
        
        chain.doFilter(request, response);
    }
    
    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/api/calendar/oauth2/callback") ||
               path.contains("/actuator") || 
               path.contains("/swagger") || 
               path.contains("/api-docs") ||
               path.contains("/v3/api-docs");
    }
}