package com.syntax.api_gateway.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;
import com.syntax.api_gateway.util.HeaderPropagador;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Service para buscar próximas entregas do usuário via usuario-service
 */
@Service
public class ProximasEntregasService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private PropriedadesMicroservicos propriedades;

    public Mono<Object> buscarProximasEntregas(String usuarioId, HttpServletRequest request) {
        String url = propriedades.getUsuario().getUrl() + "/usuario/" + usuarioId + "/proximas-entregas";
        HeaderPropagador headers = HeaderPropagador.extrairDe(request);

        return webClientBuilder.build()
            .get()
            .uri(url)
            .header("Authorization", request.getHeader("Authorization"))
            .header("X-User-Id", headers.getUserId())
            .header("X-User-Email", headers.getUserEmail())
                .header("X-User-Name", headers.getUserName())
            .header("X-Gateway-Secret", headers.getGatewaySecret())
            .retrieve()
            .bodyToMono(Object.class)
            .doOnSuccess(response -> System.out.println("✅ Próximas entregas obtidas para usuário: " + usuarioId))
            .doOnError(error -> System.err.println("❌ Erro ao buscar próximas entregas: " + error.getMessage()));
    }
}
