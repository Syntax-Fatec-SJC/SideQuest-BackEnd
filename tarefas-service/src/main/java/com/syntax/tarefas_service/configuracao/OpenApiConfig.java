package com.syntax.tarefas_service.configuracao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuração do OpenAPI/Swagger para documentação da API de Tarefas
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8084}")
    private String serverPort;

    @Bean
    public OpenAPI tarefasServiceOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Servidor de Desenvolvimento");

        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway (Produção)");

        Contact contact = new Contact();
        contact.setEmail("syntax@fatec.sp.gov.br");
        contact.setName("Equipe Syntax - FATEC SJC");
        contact.setUrl("https://github.com/Syntax-Fatec-SJC");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("SideQuest - API de Tarefas")
                .version("1.0.0")
                .contact(contact)
                .description("Microserviço responsável pelo gerenciamento de tarefas do sistema SideQuest. "
                        + "Permite criar, atualizar, listar e deletar tarefas, além de gerenciar responsáveis.")
                .termsOfService("https://sidequest.com/terms")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, gatewayServer));
    }
}
