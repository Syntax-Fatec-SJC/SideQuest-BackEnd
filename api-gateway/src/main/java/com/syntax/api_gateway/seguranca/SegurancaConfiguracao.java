package com.syntax.api_gateway.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança do API Gateway - Login e cadastro são PÚBLICOS -
 * Todos os outros endpoints são PROTEGIDOS
 */
@Configuration
@EnableWebSecurity
public class SegurancaConfiguracao {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                // ===== ENDPOINTS PÚBLICOS (LOGIN E CADASTRO) =====
                .requestMatchers(
                        // Login
                        "/usuario/login",
                        "/usuarios/login",
                        "/login",
                        "/*/login",
                        // Cadastro
                        "/usuario/cadastrar",
                        "/usuarios/cadastrar",
                        "/cadastrar",
                        "/cadastrar/**",
                        "/*/cadastrar",
                        // Documentação e monitoramento
                        "/health",
                        "/health/**",
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/actuator/**"
                ).permitAll()
                // ===== TODOS OS OUTROS ENDPOINTS SÃO PROTEGIDOS =====
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
