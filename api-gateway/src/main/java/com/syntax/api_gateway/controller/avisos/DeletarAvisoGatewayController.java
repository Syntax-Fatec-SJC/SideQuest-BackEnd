package com.syntax.api_gateway.controller.avisos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.avisos.DeletarAvisoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller para deletar avisos via Gateway
 */
@RestController
@RequestMapping("/avisos")
public class DeletarAvisoGatewayController {

    @Autowired
    private DeletarAvisoService deletarAvisoService;

    @DeleteMapping("/{avisoId}")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> deletar(@PathVariable String avisoId, HttpServletRequest request) {
        return deletarAvisoService.deletar(request.getRequestURI(), request).block();
    }

    private ResponseEntity<Object> fallbackResponse(String avisoId, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Avisos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
