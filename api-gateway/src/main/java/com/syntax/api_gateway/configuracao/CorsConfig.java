package com.syntax.api_gateway.configuracao;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuração de CORS para o API Gateway CRITICAL: Esta é a ÚNICA configuração
 * de CORS em toda a arquitetura. Os microserviços NÃO devem ter configuração de
 * CORS.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Permite credenciais (cookies, authorization headers, etc)
        config.setAllowCredentials(true);

        // Permite o frontend React - USE SEU ENDEREÇO DO FRONTEND
        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173"));

        // Permite todos os headers
        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With",
                "X-User-Id",
                "X-User-Email",
                "X-Gateway-Secret"
        ));

        // Permite todos os métodos HTTP (incluindo OPTIONS para preflight)
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS",
                "PATCH",
                "HEAD"
        ));

        // Expõe headers de resposta para o frontend
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Total-Count"
        ));

        // Define o tempo de cache do preflight (1 hora)
        config.setMaxAge(3600L);

        // Aplica para TODAS as rotas do Gateway
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
