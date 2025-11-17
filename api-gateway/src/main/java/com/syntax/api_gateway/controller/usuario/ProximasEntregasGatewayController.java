package com.syntax.api_gateway.controller.usuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.usuario.ProximasEntregasService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller para buscar próximas entregas do usuário via Gateway
 */
@RestController
@RequestMapping("/usuario")
public class ProximasEntregasGatewayController {

    @Autowired
    private ProximasEntregasService proximasEntregasService;

    @GetMapping("/{id}/proximas-entregas")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> buscarProximasEntregas(
            @PathVariable String id,
            HttpServletRequest request) {
        return ResponseEntity.ok(proximasEntregasService.buscarProximasEntregas(id, request).block());
    }

    private ResponseEntity<Object> fallbackResponse(String id, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Usuario Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
