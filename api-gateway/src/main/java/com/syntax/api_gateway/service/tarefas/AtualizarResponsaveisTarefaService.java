package com.syntax.api_gateway.service.tarefas;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;
import com.syntax.api_gateway.util.HeaderPropagador;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por atualizar responsáveis de tarefa no Tarefas Service
 */
@Service
public class AtualizarResponsaveisTarefaService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição PATCH para atualizar responsáveis da tarefa
     */
    public Mono<ResponseEntity<Object>> atualizarResponsaveis(String id, Object body, HttpServletRequest request) {
        String url = propriedades.getTarefas().getUrl() + "/tarefas/" + id + "/responsaveis";
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build().patch()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", headers.getUserId())
                .header("X-User-Email", headers.getUserEmail())
                .header("X-User-Name", headers.getUserName())
                .header("X-Gateway-Secret", headers.getGatewaySecret())
                .bodyValue(body != null ? body : Collections.emptyMap())
                .retrieve()
                .toEntity(Object.class);
    }
}
