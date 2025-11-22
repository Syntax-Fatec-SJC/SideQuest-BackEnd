package com.syntax.api_gateway.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;
import com.syntax.api_gateway.util.HeaderPropagador;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por atualizar tarefas no Tarefas Service
 */
@Service
public class AtualizarTarefaService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição PUT para atualizar tarefa completamente
     */
    public Mono<ResponseEntity<Object>> atualizarCompleto(String path, Object body, HttpServletRequest request) {
        String url = propriedades.getTarefas().getUrl() + path;
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build().put()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", headers.getUserId())
                .header("X-User-Email", headers.getUserEmail())
                .header("X-User-Name", headers.getUserName())
                .header("X-Gateway-Secret", headers.getGatewaySecret())
                .bodyValue(body)
                .retrieve()
                .toEntity(Object.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Repassa o erro 4xx ou 5xx com o corpo original da resposta
                    return Mono.just(ResponseEntity
                            .status(ex.getStatusCode())
                            .body((Object) ex.getResponseBodyAsString()));
                });
    }

    /**
     * Realiza requisição PATCH para atualizar tarefa parcialmente
     */
    public Mono<ResponseEntity<Object>> atualizarParcial(String path, Object body, HttpServletRequest request) {
        String url = propriedades.getTarefas().getUrl() + path;
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build().patch()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", headers.getUserId())
                .header("X-User-Email", headers.getUserEmail())
                .header("X-User-Name", headers.getUserName())
                .header("X-Gateway-Secret", headers.getGatewaySecret())
                .bodyValue(body)
                .retrieve()
                .toEntity(Object.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Repassa o erro 4xx ou 5xx com o corpo original da resposta
                    return Mono.just(ResponseEntity
                            .status(ex.getStatusCode())
                            .body((Object) ex.getResponseBodyAsString()));
                });
    }
}
