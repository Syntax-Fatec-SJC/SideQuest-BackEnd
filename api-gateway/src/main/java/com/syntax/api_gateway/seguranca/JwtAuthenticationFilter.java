package com.syntax.api_gateway.seguranca;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticação JWT para o API Gateway
 * Valida tokens JWT e propaga informações do usuário para microserviços
 */
@Component
@Order(2) // Executa depois do LoggingFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private static final String GATEWAY_SECRET = "SideQuestGatewaySecret2024";

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) 
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Endpoints públicos não precisam de token
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrai o token
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;
        String email = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(jwt);
            } catch (Exception e) {
                logger.error("❌ Erro ao extrair email do token: {}", e.getMessage());
            }
        }

        // Se não tem token, bloqueia
        if (jwt == null) {
            logger.warn("❌ Token ausente: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"Token JWT ausente\"}");
            return;
        }

        // Valida o token
        if (!jwtUtil.validateToken(jwt)) {
            logger.warn("❌ Token inválido ou expirado: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"Token JWT inválido ou expirado\"}");
            return;
        }

        // Extrai informações do usuário
        String userId = jwtUtil.extractUserId(jwt);
        String nome = jwtUtil.extractNome(jwt);
        final String finalEmail = email;
        final String finalUserId = userId;
        final String finalNome = nome;

        logger.info("✅ Token válido - User: {} ({}) - Nome: {}", finalEmail, finalUserId, finalNome);

        // Cria autenticação no contexto Spring Security
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(
                finalEmail, 
                null, 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Adiciona headers como atributos do request para serem propagados aos microserviços
        request.setAttribute("X-User-Id", finalUserId);
        request.setAttribute("X-User-Email", finalEmail);
        request.setAttribute("X-User-Name", finalNome);
        request.setAttribute("X-Gateway-Secret", GATEWAY_SECRET);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Não aplica filtro em endpoints públicos
        return path.equals("/") ||
               path.equals("/health") ||
               path.contains("/health/") ||
               path.contains("/usuario/login") ||
               path.contains("/usuario/cadastrar") ||
               path.contains("/actuator") ||
               path.contains("/swagger") ||
               path.contains("/api-docs") ||
               path.startsWith("/v3/api-docs");
    }
}
