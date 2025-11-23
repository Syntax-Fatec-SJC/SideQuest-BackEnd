package com.syntax.api_gateway.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;
import com.syntax.api_gateway.util.HeaderPropagador;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por atualizar usuários no Usuario Service
 */
@Service
public class AtualizarUsuarioService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição PUT para atualizar usuário
     */
    public Mono<ResponseEntity<Object>> atualizar(String id, Object body, HttpServletRequest request) {
        String url = propriedades.getUsuario().getUrl() + "/api/usuarios/" + id;
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
                .toEntity(Object.class);
    }
}
