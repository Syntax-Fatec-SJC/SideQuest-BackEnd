package com.syntax.api_gateway.controller.usuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.usuario.AtualizarUsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para atualizar usuários via Gateway
 */
@RestController
@RequestMapping("/usuario")
public class AtualizarUsuarioGatewayController {

    @Autowired
    private AtualizarUsuarioService atualizarUsuarioService;

    @PutMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public Mono<ResponseEntity<Object>> atualizar(@RequestBody Object body, HttpServletRequest request) {
        String path = request.getRequestURI();
        return atualizarUsuarioService.atualizar(path, body, request);
    }

    private Mono<ResponseEntity<Object>> fallbackResponse(Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Usuario Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error));
    }
}
