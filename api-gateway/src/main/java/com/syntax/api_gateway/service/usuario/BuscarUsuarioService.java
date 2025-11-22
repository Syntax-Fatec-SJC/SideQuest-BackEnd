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
 * Service responsável por buscar usuários do Usuario Service
 */
@Service
public class BuscarUsuarioService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição GET para buscar usuário
     */
    public Mono<ResponseEntity<Object>> buscar(String id, HttpServletRequest request) {
        String url = propriedades.getUsuario().getUrl() + "/usuarios/" + id;
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build().get()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", headers.getUserId())
                .header("X-User-Email", headers.getUserEmail())
                .header("X-User-Name", headers.getUserName())
                .header("X-Gateway-Secret", headers.getGatewaySecret())
                .retrieve()
                .toEntity(Object.class);
    }
}
