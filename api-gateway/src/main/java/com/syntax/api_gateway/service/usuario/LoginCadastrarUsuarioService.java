package com.syntax.api_gateway.service.usuario;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service responsável por login e cadastro de usuários no Usuario Service
 */
@Service
public class LoginCadastrarUsuarioService {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private static final String GATEWAY_SECRET = "SideQuestGatewaySecret2024";

    /**
     * Realiza requisição POST para login ou cadastro de usuário
     * Nota: Endpoints públicos não têm JWT, mas ainda precisam do X-Gateway-Secret
     */
    public Mono<ResponseEntity<Object>> processar(String path, Object body, HttpServletRequest request) {
        // Remove o prefixo /usuario/ do path, pois o microsserviço espera apenas /login ou /cadastrar
        String targetPath = path.replace("/usuario", "");
        String url = propriedades.getUsuario().getUrl() + targetPath;

        return webClientBuilder.build().post()
                .uri(url)
                .header("X-Gateway-Secret", GATEWAY_SECRET)
                .bodyValue(body != null ? body : Collections.emptyMap())
                .retrieve()
                .toEntity(Object.class);
    }
}
