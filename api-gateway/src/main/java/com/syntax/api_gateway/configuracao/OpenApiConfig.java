package com.syntax.api_gateway.configuracao;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuração do Swagger/OpenAPI para o API Gateway
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("API Gateway - Servidor de Desenvolvimento");

        Contact contact = new Contact();
        contact.setName("Equipe Syntax - FATEC SJC");
        contact.setUrl("https://github.com/Syntax-Fatec-SJC");
        contact.setEmail("syntax@fatec.sp.gov.br");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("SideQuest - API Gateway")
                .version("1.0.0")
                .description("API Gateway para roteamento e gerenciamento de microserviços do sistema SideQuest. " +
                            "Este gateway centraliza todas as requisições e roteia para os microserviços apropriados: " +
                            "Usuario Service, Projetos Service e Tarefas Service.")
                .termsOfService("https://sidequest.com/terms")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
