package com.syntax.lixeira_service.seguranca;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HeaderToAttributeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrai headers e coloca como atributos
        String userId = request.getHeader("X-User-Id");
        String userEmail = request.getHeader("X-User-Email");

        if (userId != null) {
            request.setAttribute("userId", userId);
        }
        if (userEmail != null) {
            request.setAttribute("userEmail", userEmail);
        }

        filterChain.doFilter(request, response);
    }
}
