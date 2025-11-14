package com.fatec.anexo_service.seguranca;

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

@Component
@Order(1)
public class GatewayAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);
    private static final String GATEWAY_SECRET_HEADER = "X-Gateway-Secret";
    private static final String GATEWAY_SECRET = "SideQuestGatewaySecret2024";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_EMAIL_HEADER = "X-User-Email";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();

        // Endpoints públicos
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se vem do Gateway
        String gatewaySecret = httpRequest.getHeader(GATEWAY_SECRET_HEADER);

        if (gatewaySecret == null || !gatewaySecret.equals(GATEWAY_SECRET)) {
            logger.warn(" Acesso direto bloqueado: {}", path);
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(
                    "{\"erro\":\"Acesso direto não permitido. Use o API Gateway (porta 8080).\"}"
            );
            return;
        }

        // Extrai informações do usuário
        String userId = httpRequest.getHeader(USER_ID_HEADER);
        String userEmail = httpRequest.getHeader(USER_EMAIL_HEADER);

        if (userId == null || userEmail == null) {
            logger.warn(" Headers de autenticação ausentes: {}", path);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }

        httpRequest.setAttribute("userId", userId);
        httpRequest.setAttribute("userEmail", userEmail);

        logger.debug(" Requisição autenticada - User: {} ({})", userEmail, userId);
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
