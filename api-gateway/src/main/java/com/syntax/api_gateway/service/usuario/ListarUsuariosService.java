package com.syntax.api_gateway.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por listar usuários no Usuario Service
 */
@Service
public class ListarUsuariosService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient webClient;

    /**
     * Realiza requisição GET para listar todos os usuários
     */
    public Mono<ResponseEntity<Object>> listar(HttpServletRequest request) {
        String url = propriedades.getUsuario().getUrl() + "/usuarios";

        return webClient.get()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .retrieve()
                .toEntity(Object.class);
    }
}
