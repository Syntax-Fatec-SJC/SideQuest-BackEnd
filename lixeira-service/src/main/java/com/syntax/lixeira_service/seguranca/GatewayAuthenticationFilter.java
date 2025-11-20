package com.syntax.lixeira_service.seguranca;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
@Order(1)
public class GatewayAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        logger.info("[{}] {}", method, path);

        // Endpoints públicos
        if (path.contains("/actuator") || path.contains("/swagger") || path.contains("/api-docs")) {
            logger.info("Endpoint publico liberado");
            chain.doFilter(request, response);
            return;
        }

        // Extrair JWT
        String authHeader = httpRequest.getHeader("Authorization");
        logger.info("Authorization header: {}", authHeader != null ? "presente" : "ausente");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Token nao fornecido ou formato invalido");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("{\"erro\":\"Token nao fornecido\"}");
            return;
        }

        try {
            String token = authHeader.substring(7);
            logger.debug("Token recebido (primeiros 20 chars): {}", token.substring(0, Math.min(20, token.length())));

            String[] parts = token.split("\\.");
            logger.info("Partes do JWT: {}", parts.length);

            if (parts.length != 3) {
                throw new IllegalArgumentException("Token deve ter 3 partes");
            }

            // Decodificar payload
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            logger.info("Payload decodificado com sucesso");

            @SuppressWarnings("unchecked")
            Map<String, Object> claims = objectMapper.readValue(payload, Map.class);
            logger.info("Claims extraidos: {}", claims.keySet());

            String userId = (String) claims.get("userId");
            String userEmail = (String) claims.get("sub");

            logger.info("userId: {} | email: {}", userId, userEmail);

            if (userId == null) {
                userId = userEmail;
                logger.info("userId era null, usando email como userId");
            }

            if (userEmail == null) {
                logger.warn("Email (sub) nao encontrado no token");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.getWriter().write("{\"erro\":\"Email nao encontrado no token\"}");
                return;
            }

            httpRequest.setAttribute("userId", userId);
            httpRequest.setAttribute("userEmail", userEmail);

            logger.info("Autenticacao OK - Prosseguindo para {}", path);
            chain.doFilter(request, response);

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao decodificar JWT: {}", e.getMessage());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("{\"erro\":\"Token invalido: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Erro geral: {}", e.getMessage(), e);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("{\"erro\":\"Erro ao processar token\"}");
        }
    }
}
