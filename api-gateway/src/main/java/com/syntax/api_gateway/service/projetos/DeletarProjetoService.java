package com.syntax.api_gateway.service.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;
import com.syntax.api_gateway.util.HeaderPropagador;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por deletar projetos do Projetos Service
 */
@Service
public class DeletarProjetoService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição DELETE para deletar projeto
     */
    public Mono<ResponseEntity<Object>> deletar(String path, HttpServletRequest request) {
        String url = propriedades.getProjetos().getUrl() + path;
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build().delete()
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
