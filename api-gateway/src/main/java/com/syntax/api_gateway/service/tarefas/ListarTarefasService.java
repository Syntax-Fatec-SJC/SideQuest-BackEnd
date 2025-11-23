package com.syntax.api_gateway.service.tarefas;

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
 * Service responsável por listar tarefas do Tarefas Service
 */
@Service
public class ListarTarefasService {

    private static final Logger logger = LoggerFactory.getLogger(ListarTarefasService.class);

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição GET para listar tarefas
     */
    public Mono<ResponseEntity<Object>> listar(String path, HttpServletRequest request) {
        String url = propriedades.getTarefas().getUrl() + path;
        
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        logger.info("🔍 [ListarTarefasService] URL: {}", url);
        logger.info("🔍 [ListarTarefasService] X-User-Id: {}", headers.getUserId());
        logger.info("🔍 [ListarTarefasService] X-User-Email: {}", headers.getUserEmail());
        logger.info("🔍 [ListarTarefasService] X-Gateway-Secret: {}", headers.getGatewaySecret());

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
