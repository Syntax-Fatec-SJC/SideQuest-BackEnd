package com.syntax.api_gateway.controller.avisos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.avisos.ListarAvisosService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller para listar avisos via Gateway
 */
@RestController
@RequestMapping("/avisos")
public class ListarAvisosGatewayController {

    @Autowired
    private ListarAvisosService listarAvisosService;

    @GetMapping("/usuario/{usuarioId}")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> listarPorUsuario(@PathVariable String usuarioId, HttpServletRequest request) {
        return listarAvisosService.listar(request.getRequestURI(), request).block();
    }

    private ResponseEntity<Object> fallbackResponse(String usuarioId, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Avisos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
