package com.syntax.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

/**
 * Aplicação principal do API Gateway
 * 
 * Este API Gateway é responsável por:
 * - Rotear requisições para os microserviços (Usuario, Projetos, Tarefas)
 * - Validar tokens JWT
 * - Aplicar Circuit Breaker e Rate Limiting
 * - Gerenciar CORS
 * - Agregar documentação Swagger
 */
@SpringBootApplication
@EnableConfigurationProperties(PropriedadesMicroservicos.class)
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

