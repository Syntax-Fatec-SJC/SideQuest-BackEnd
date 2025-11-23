package com.syntax.api_gateway.controller.usuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.usuario.ListarUsuariosService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para listar usuários via Gateway
 */
@RestController
@RequestMapping("/listar/usuarios")
public class ListarUsuariosGatewayController {

    @Autowired
    private ListarUsuariosService listarUsuariosService;

    @GetMapping
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> listar(HttpServletRequest request) {
        return listarUsuariosService.listar(request).block();
    }

    private ResponseEntity<Object> fallbackResponse(HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Usuario Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
