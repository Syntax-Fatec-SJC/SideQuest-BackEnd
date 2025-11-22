package com.syntax.api_gateway.service.projetos;

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
 * Service responsável por cadastrar projetos no Projetos Service
 */
@Service
public class CadastrarProjetoService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Realiza requisição POST para cadastrar projeto
     */
    public Mono<ResponseEntity<Object>> cadastrar(String path, Object body, HttpServletRequest request) {
        String url = propriedades.getProjetos().getUrl() + path;
        
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

        // Extrai headers dos atributos do request (adicionados pelo JwtAuthenticationFilter)
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build().post()
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
