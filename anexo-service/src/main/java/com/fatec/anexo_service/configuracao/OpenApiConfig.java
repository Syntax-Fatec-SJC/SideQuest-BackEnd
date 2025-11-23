package com.fatec.anexo_service.configuracao;

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
 * Configuração do OpenAPI/Swagger para documentação da API de Anexos.
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8084}")
    private String serverPort;

    @Bean
    public OpenAPI anexosServiceOpenAPI() {
        // configuração dos servidores
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Servidor de Desenvolvimento");

        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("Servidor Gateway (Produção/Exposição)");

        // Configuração de Contato
        Contact contact = new Contact();
        contact.setEmail("dev@fatec.com");
        contact.setName("Fatec Dev Team");
        contact.setUrl("https://fatec.com");

        // Configuração de Licença
        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        // Informações gerais da API
        Info info = new Info()
                .title("SideQuest - API de Anexos")
                .version("1.0.0")
                .contact(contact)
                .description("Microserviço responsável pelo gerenciamento de anexos do sistema SideQuest. "
                        + "Permite upload, download, listagem e exclusão de arquivos (imagens, PDFs e vídeos) "
                        + "associados às tarefas. Suporta múltiplos arquivos por tarefa com validação de tipo e tamanho.")
                .termsOfService("https://sidequest.com/terms")
                .license(license);

        // Configuração de segurança JWT
        final String securitySchemeName = "bearerAuth";

        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Insira o token JWT no formato: Bearer <seu_token>");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        // Retorno da configuração completa do OpenAPI
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, gatewayServer))
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes(securitySchemeName, securityScheme));
    }
}
