package com.syntax.usuario_service.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança SIMPLIFICADA
 * - Login e Cadastro são públicos (geram token)
 * - Outros endpoints confiam no API Gateway (que já validou o JWT)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // Usa CorsConfig
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (geram token)
                .requestMatchers("/login", "/cadastrar").permitAll()
                
                // Swagger/Health (monitoramento)
                .requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Outros endpoints: confia no Gateway (que já validou JWT)
                .anyRequest().permitAll()
            );
        
        return http.build();
    }
}
