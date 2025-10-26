package com.syntax.sidequest_backend.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    private static String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI configuracao(){
        return new OpenAPI()
            .info(new Info()
                .title("Sidequest API")
                .version("1.0.0")
                .description("Documentação da API Sidequest com JWT"))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, 
                    new SecurityScheme()
                    .name(SECURITY_SCHEME_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
}
