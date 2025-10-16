package com.syntax.sidequest_backend.segurança;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = extrairTokenDaRequisicao(request);

        if (token != null) {
            try {
                String email = jwtUtil.extractEmail(token);
                UsernamePasswordAuthenticationToken auth
                        = new UsernamePasswordAuthenticationToken(email, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Token inválido
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extrairTokenDaRequisicao(HttpServletRequest request) {
        String cabecalhoAutorizacao = request.getHeader("Authorization");

        if (cabecalhoAutorizacao != null
                && cabecalhoAutorizacao.startsWith("Bearer ")) {
            return cabecalhoAutorizacao.substring(7);
        }

        return null;
    }
}
