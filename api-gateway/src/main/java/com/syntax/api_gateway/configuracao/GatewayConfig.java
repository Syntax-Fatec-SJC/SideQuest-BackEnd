package com.syntax.api_gateway.configuracao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Value("${microservices.usuario.url}")
    private String usuarioServiceUrl;

    @Value("${microservices.projetos.url}")
    private String projetosServiceUrl;

    @Value("${microservices.tarefas.url}")
    private String tarefasServiceUrl;

    @Value("${microservices.anexos.url}")
    private String anexosServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> usuarioRoute() {
        return route("usuarios-service")
                .route(request -> {
                    String path = request.path();
                    return path.startsWith("/usuarios")
                            || path.startsWith("/usuario")
                            || path.startsWith("/login")
                            || (path.startsWith("/cadastrar") && !path.startsWith("/cadastrar/tarefas") && !path.startsWith("/cadastrar/projetos"));
                }, http(usuarioServiceUrl))
                .before(BeforeFilterFunctions.addRequestHeader("X-Gateway-Secret", "SideQuestGatewaySecret2024"))
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Id") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Id",
                                (String) request.servletRequest().getAttribute("X-User-Id")).apply(request);
                    }
                    return request;
                })
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Email") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Email",
                                (String) request.servletRequest().getAttribute("X-User-Email")).apply(request);
                    }
                    return request;
                })
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> projetosRoute() {
        return route("projetos-service")
                .route(request -> {
                    String path = request.path();
                    return path.startsWith("/projetos")
                            || path.startsWith("/cadastrar/projetos")
                            || path.startsWith("/listar/projetos");
                }, http(projetosServiceUrl))
                .before(BeforeFilterFunctions.addRequestHeader("X-Gateway-Secret", "SideQuestGatewaySecret2024"))
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Id") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Id",
                                (String) request.servletRequest().getAttribute("X-User-Id")).apply(request);
                    }
                    return request;
                })
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Email") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Email",
                                (String) request.servletRequest().getAttribute("X-User-Email")).apply(request);
                    }
                    return request;
                })
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> tarefasRoute() {
        return route("tarefas-service")
                .route(request -> {
                    String path = request.path();
                    return path.startsWith("/tarefas")
                            || path.startsWith("/cadastrar/tarefas")
                            || path.startsWith("/listar/tarefas");
                }, http(tarefasServiceUrl))
                .before(BeforeFilterFunctions.addRequestHeader("X-Gateway-Secret", "SideQuestGatewaySecret2024"))
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Id") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Id",
                                (String) request.servletRequest().getAttribute("X-User-Id")).apply(request);
                    }
                    return request;
                })
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Email") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Email",
                                (String) request.servletRequest().getAttribute("X-User-Email")).apply(request);
                    }
                    return request;
                })
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> anexosRoute() {
        return route("anexos-service")
                .route(request -> {
                    String path = request.path();
                    return path.startsWith("/api/anexos");
                }, http(anexosServiceUrl))
                .before(BeforeFilterFunctions.addRequestHeader("X-Gateway-Secret", "SideQuestGatewaySecret2024"))
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Id") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Id",
                                (String) request.servletRequest().getAttribute("X-User-Id")).apply(request);
                    }
                    return request;
                })
                .before(request -> {
                    if (request.servletRequest().getAttribute("X-User-Email") != null) {
                        return BeforeFilterFunctions.addRequestHeader("X-User-Email",
                                (String) request.servletRequest().getAttribute("X-User-Email")).apply(request);
                    }
                    return request;
                })
                .build();
    }
}
