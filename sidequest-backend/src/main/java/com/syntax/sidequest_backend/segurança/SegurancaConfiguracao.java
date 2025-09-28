package com.syntax.sidequest_backend.segurança;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SegurancaConfiguracao {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
	public SecurityFilterChain rotasPublicas(HttpSecurity http) throws Exception {
		http
		.cors(Customizer.withDefaults())
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/cadastrar/usuarios", "/login")
				.permitAll()
				.requestMatchers(
					"/listar/**",
					"/cadastrar/projetos",
					"/atualizar/projetos/**",
					"/excluir/projetos/**",
					"/cadastrar/tarefas",
					"/atualizar/tarefas/**",
					"/excluir/tarefas/**",
					"/projetos/**/tarefas",
					"/usuarios/**/tarefas"
				)
				.permitAll()
				.anyRequest().authenticated()
		)
		.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean public CorsConfigurationSource corsConfigurationSource(){
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
