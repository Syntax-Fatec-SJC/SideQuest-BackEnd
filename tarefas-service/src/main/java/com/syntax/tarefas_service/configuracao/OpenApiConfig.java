package com.syntax.tarefas_service.configuracao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuração do OpenAPI/Swagger para documentação da API de Tarefas
 */
@Configuration
public class OpenApiConfig {

    // Porta do serviço
    @Value("${server.port:8084}")
    private String serverPort;

    @Bean
    public OpenAPI tarefasServiceOpenAPI() {
        // Servidor do serviço local (dev)
        Server devServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Servidor de Desenvolvimento");

        // Contato e licença
        Contact contact = new Contact()
                .name("Equipe Syntax - FATEC SJC")
                .email("syntax@fatec.sp.gov.br")
                .url("https://github.com/Syntax-Fatec-SJC");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("SideQuest - API de Tarefas")
                .version("1.0.0")
                .description("Microserviço responsável pelo gerenciamento de tarefas do sistema SideQuest. "
                        + "Permite criar, atualizar, listar e deletar tarefas, além de gerenciar responsáveis.")
                .contact(contact)
                .termsOfService("https://sidequest.com/terms")
                .license(license);

        // Configuração de Segurança JWT
        String securitySchemeName = "bearerAuth";

        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Insira o token JWT obtido no login");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer)) // apenas o servidor do serviço
                .components(new Components().addSecuritySchemes(securitySchemeName, securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
