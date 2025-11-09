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
 * Service responsável por cadastrar projetos no Projetos Service
 */
@Service
public class CadastrarProjetoService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient webClient;

    /**
     * Realiza requisição POST para cadastrar projeto
     */
    public Mono<ResponseEntity<Object>> cadastrar(String path, Object body, HttpServletRequest request) {
        String url = propriedades.getProjetos().getUrl() + path;
        
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

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
}
