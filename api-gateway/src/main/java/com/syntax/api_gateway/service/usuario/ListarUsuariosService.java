package com.syntax.api_gateway.service.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;
import com.syntax.api_gateway.util.HeaderPropagador;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por listar usuários no Usuario Service
 */
@Service
public class ListarUsuariosService {

    private static final Logger logger = LoggerFactory.getLogger(ListarUsuariosService.class);

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição GET para listar todos os usuários
     */
    public Mono<ResponseEntity<Object>> listar(HttpServletRequest request) {
        String url = propriedades.getUsuario().getUrl() + "/listar/usuarios";
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        logger.info("🔍 [ListarUsuariosService] URL: {}", url);
        logger.info("🔍 [ListarUsuariosService] X-User-Id: {}", headers.getUserId());
        logger.info("🔍 [ListarUsuariosService] X-User-Email: {}", headers.getUserEmail());
        logger.info("🔍 [ListarUsuariosService] X-Gateway-Secret: {}", headers.getGatewaySecret());
        logger.info("🔍 [ListarUsuariosService] Authorization: {}", request.getHeader("Authorization") != null ? "presente" : "ausente");

        return webClientBuilder.build().get()
                .uri(url)
                .headers(h -> {
                    if (request.getHeader("Authorization") != null) {
                        h.set("Authorization", request.getHeader("Authorization"));
                    }
                    if (headers.getUserId() != null) {
                        h.set("X-User-Id", headers.getUserId());
                    }
                    if (headers.getUserEmail() != null) {
                        h.set("X-User-Email", headers.getUserEmail());
                    }
                    if (headers.getGatewaySecret() != null) {
                        h.set("X-Gateway-Secret", headers.getGatewaySecret());
                    }
                })
                .retrieve()
                .toEntity(Object.class);
    }
}
