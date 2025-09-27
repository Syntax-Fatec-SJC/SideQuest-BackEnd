package com.syntax.sidequest_backend.segurança;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/cadastrar/usuarios", "/login").permitAll())
		.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
