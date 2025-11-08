package com.syntax.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

import reactor.core.publisher.Mono;

/**
 * Serviço para facilitar a comunicação com os microserviços
 */
@Service
public class MicroserviceProxyService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private PropriedadesMicroservicos propriedades;

    /**
     * Faz uma requisição GET para um microserviço
     */
    public <T> Mono<T> get(String serviceName, String path, String token, Class<T> responseType) {
        String baseUrl = getServiceUrl(serviceName);
        
        return webClient.get()
                .uri(baseUrl + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Faz uma requisição POST para um microserviço
     */
    public <T, R> Mono<R> post(String serviceName, String path, String token, T body, Class<R> responseType) {
        String baseUrl = getServiceUrl(serviceName);
        
        return webClient.post()
                .uri(baseUrl + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Faz uma requisição PUT para um microserviço
     */
    public <T, R> Mono<R> put(String serviceName, String path, String token, T body, Class<R> responseType) {
        String baseUrl = getServiceUrl(serviceName);
        
        return webClient.put()
                .uri(baseUrl + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Faz uma requisição PATCH para um microserviço
     */
    public <T, R> Mono<R> patch(String serviceName, String path, String token, T body, Class<R> responseType) {
        String baseUrl = getServiceUrl(serviceName);
        
        return webClient.patch()
                .uri(baseUrl + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Faz uma requisição DELETE para um microserviço
     */
    public <T> Mono<T> delete(String serviceName, String path, String token, Class<T> responseType) {
        String baseUrl = getServiceUrl(serviceName);
        
        return webClient.delete()
                .uri(baseUrl + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Obtém a URL base de um microserviço pelo nome
     */
    private String getServiceUrl(String serviceName) {
        return switch (serviceName.toLowerCase()) {
            case "usuario", "usuario-service" -> propriedades.getUsuario().getUrl();
            case "projetos", "projetos-service" -> propriedades.getProjetos().getUrl();
            case "tarefas", "tarefas-service" -> propriedades.getTarefas().getUrl();
            default -> throw new IllegalArgumentException("Serviço desconhecido: " + serviceName);
        };
    }
}
