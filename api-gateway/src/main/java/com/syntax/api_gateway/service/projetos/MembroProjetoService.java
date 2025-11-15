package com.syntax.api_gateway.service.projetos;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por gerenciar membros de projeto no Projetos Service
 */
@Service
public class MembroProjetoService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient webClient;

    /**
     * Adiciona um membro ao projeto
     */
    public Mono<ResponseEntity<Object>> adicionarMembro(String projetoId, Object body, HttpServletRequest request) {
        String url = propriedades.getProjetos().getUrl() + "/projetos/" + projetoId + "/membros";

        return webClient.post()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .bodyValue(body != null ? body : Collections.emptyMap())
                .retrieve()
                .toEntity(Object.class);
    }

    /**
     * Lista membros do projeto
     */
    public Mono<ResponseEntity<Object>> listarMembros(String projetoId, HttpServletRequest request) {
        String url = propriedades.getProjetos().getUrl() + "/projetos/" + projetoId + "/membros";

        return webClient.get()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .retrieve()
                .toEntity(Object.class);
    }

    /**
     * Remove um membro do projeto
     */
    public Mono<ResponseEntity<Object>> removerMembro(String projetoId, String usuarioId, HttpServletRequest request) {
        String url = propriedades.getProjetos().getUrl() + "/projetos/" + projetoId + "/membros/" + usuarioId;

        return webClient.delete()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .retrieve()
                .toEntity(Object.class);
    }
}
