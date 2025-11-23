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

/**
 * ⚠️ FILTRO TEMPORARIAMENTE DESABILITADO PARA TESTES
 *
 * Este filtro está permitindo TODAS as requisições enquanto testamos a
 * integração com o frontend.
 *
 * TODO: Reabilitar após configurar o Gateway corretamente
 */
@Component
@Order(1)
public class GatewayAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        logger.debug(" [FILTRO DESABILITADO] Permitindo requisição: {}", path);
        chain.doFilter(request, response);

        /* 
         * ========================================
         * CÓDIGO ORIGINAL (COMENTADO)
         * ========================================
         * Descomente quando o Gateway estiver configurado
         * 
        String gatewaySecret = httpRequest.getHeader("X-Gateway-Secret");
        
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        if (gatewaySecret == null || !gatewaySecret.equals("SideQuestGatewaySecret2024")) {
            logger.warn(" Gateway Secret inválido - Path: {}", path);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"erro\":\"Acesso negado. Gateway Secret inválido.\"}");
            return;
        }
        
        chain.doFilter(request, response);
         */
    }

    private boolean isPublicEndpoint(String path) {
        return path.contains("/actuator")
                || path.contains("/swagger")
                || path.contains("/api-docs")
                || path.contains("/v3/api-docs")
                || path.contains("/health");
    }
}
