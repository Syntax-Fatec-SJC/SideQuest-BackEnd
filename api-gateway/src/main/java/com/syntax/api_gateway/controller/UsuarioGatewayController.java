package com.syntax.api_gateway.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller responsável por rotear requisições para o Usuario Service
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioGatewayController {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient webClient;

    /**
     * Roteia todas as requisições GET para o Usuario Service
     */
    @GetMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public Mono<ResponseEntity<Object>> getRequest(HttpServletRequest request) {
        String path = request.getRequestURI().replace("/usuario", "");
        String url = propriedades.getUsuario().getUrl() + path;
        
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

        return webClient.get()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .retrieve()
                .toEntity(Object.class);
    }

    /**
     * Roteia todas as requisições POST para o Usuario Service
     */
    @PostMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public Mono<ResponseEntity<Object>> postRequest(HttpServletRequest request, @RequestBody(required = false) Object body) {
        String path = request.getRequestURI().replace("/usuario", "");
        String url = propriedades.getUsuario().getUrl() + path;

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

    /**
     * Roteia todas as requisições PUT para o Usuario Service
     */
    @PutMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public Mono<ResponseEntity<Object>> putRequest(HttpServletRequest request, @RequestBody Object body) {
        String path = request.getRequestURI().replace("/usuario", "");
        String url = propriedades.getUsuario().getUrl() + path;

        return webClient.put()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .bodyValue(body)
                .retrieve()
                .toEntity(Object.class);
    }

    /**
     * Roteia todas as requisições DELETE para o Usuario Service
     */
    @DeleteMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public Mono<ResponseEntity<Object>> deleteRequest(HttpServletRequest request) {
        String path = request.getRequestURI().replace("/usuario", "");
        String url = propriedades.getUsuario().getUrl() + path;

        return webClient.delete()
                .uri(url)
                .header("Authorization", request.getHeader("Authorization"))
                .header("X-User-Id", request.getHeader("X-User-Id"))
                .header("X-User-Email", request.getHeader("X-User-Email"))
                .header("X-Gateway-Secret", request.getHeader("X-Gateway-Secret"))
                .retrieve()
                .toEntity(Object.class);
    }

    /**
     * Método fallback quando o serviço está indisponível
     */
    private Mono<ResponseEntity<Object>> fallbackResponse(Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Usuario Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error));
    }
}
