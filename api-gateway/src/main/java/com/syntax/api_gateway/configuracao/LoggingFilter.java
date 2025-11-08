package com.syntax.api_gateway.configuracao;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro para logging de requisições e adicionar correlation ID
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                  @NonNull HttpServletResponse response, 
                                  @NonNull FilterChain filterChain) 
            throws ServletException, java.io.IOException {
        
        long startTime = System.currentTimeMillis();
        
        // Gera ou obtém o correlation ID
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        
        // Adiciona o correlation ID na resposta
        response.setHeader(CORRELATION_ID_HEADER, correlationId);
        
        // Adiciona na requisição para propagação
        request.setAttribute(CORRELATION_ID_HEADER, correlationId);
        
        // Log da requisição
        log.info("🔵 [{}] {} {} - IP: {}", 
            correlationId,
            request.getMethod(), 
            request.getRequestURI(),
            getClientIpAddress(request)
        );
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            
            // Log da resposta
            String statusEmoji = getStatusEmoji(response.getStatus());
            log.info("{} [{}] {} {} - Status: {} - Tempo: {}ms", 
                statusEmoji,
                correlationId,
                request.getMethod(), 
                request.getRequestURI(),
                response.getStatus(),
                duration
            );
        }
    }

    /**
     * Obtém o IP real do cliente, considerando proxies
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    /**
     * Retorna emoji baseado no status code
     */
    private String getStatusEmoji(int status) {
        if (status >= 200 && status < 300) return "✅";
        if (status >= 300 && status < 400) return "🔄";
        if (status >= 400 && status < 500) return "⚠️";
        if (status >= 500) return "❌";
        return "ℹ️";
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        // Não aplica filtro em endpoints de health check e actuator
        return path.startsWith("/actuator") || 
               path.startsWith("/swagger-ui") || 
               path.startsWith("/api-docs") ||
               path.startsWith("/v3/api-docs");
    }
}
