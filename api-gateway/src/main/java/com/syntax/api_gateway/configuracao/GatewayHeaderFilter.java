package com.syntax.api_gateway.configuracao;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.syntax.api_gateway.seguranca.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que adiciona headers do Gateway ANTES de rotear
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayHeaderFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(GatewayHeaderFilter.class);
    private static final String GATEWAY_SECRET = "SideQuestGatewaySecret2024";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Adiciona header do Gateway como atributo para ser usado pelo proxy
        request.setAttribute("X-Gateway-Secret", GATEWAY_SECRET);

        // Extrai token e informações do usuário
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            try {
                String userId = jwtUtil.extractUserId(jwt);
                String userEmail = jwtUtil.extractEmail(jwt);

                if (userId != null) {
                    request.setAttribute("X-User-Id", userId);
                    logger.info("✅ Extraiu userId: {} para path: {}", userId, path);
                }
                if (userEmail != null) {
                    request.setAttribute("X-User-Email", userEmail);
                    logger.info("✅ Extraiu userEmail: {} para path: {}", userEmail, path);
                }
            } catch (Exception e) {
                logger.warn("⚠️ Erro ao extrair informações do token: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
