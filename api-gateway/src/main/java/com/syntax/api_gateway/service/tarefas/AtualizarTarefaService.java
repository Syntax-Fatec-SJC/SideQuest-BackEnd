package com.syntax.api_gateway.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

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
    private WebClient webClient;

    /**
     * Realiza requisição PUT para atualizar tarefa completamente
     */
    public Mono<ResponseEntity<Object>> atualizarCompleto(String path, Object body, HttpServletRequest request) {
        String url = propriedades.getTarefas().getUrl() + path;

        return webClient.put()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .bodyValue(body)
                .retrieve()
                .toEntity(Object.class);
    }

    /**
     * Realiza requisição PATCH para atualizar tarefa parcialmente
     */
    public Mono<ResponseEntity<Object>> atualizarParcial(String path, Object body, HttpServletRequest request) {
        String url = propriedades.getTarefas().getUrl() + path;

        return webClient.patch()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .bodyValue(body)
                .retrieve()
                .toEntity(Object.class);
    }
}
