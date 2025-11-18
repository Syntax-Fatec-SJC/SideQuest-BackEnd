package com.syntax.api_gateway.controller.projetos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.projetos.MembroProjetoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para gerenciar membros de projetos via Gateway
 */
@RestController
@RequestMapping("/projetos")
public class MembroProjetoGatewayController {

    @Autowired
    private MembroProjetoService membroProjetoService;

    @PostMapping("/{projetoId}/membros")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> adicionarMembro(
            @PathVariable String projetoId,
            @RequestBody Object body, 
            HttpServletRequest request) {
        return membroProjetoService.adicionarMembro(projetoId, body, request).block();
    }

    @GetMapping("/{projetoId}/membros")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponseSemBody")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> listarMembros(
            @PathVariable String projetoId,
            HttpServletRequest request) {
        return membroProjetoService.listarMembros(projetoId, request).block();
    }

    @DeleteMapping("/{projetoId}/membros/{usuarioId}")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponseSemBody")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> removerMembro(
            @PathVariable String projetoId,
            @PathVariable String usuarioId,
            HttpServletRequest request) {
        return membroProjetoService.removerMembro(projetoId, usuarioId, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(String projetoId, Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Projetos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    private ResponseEntity<Object> fallbackResponseSemBody(String projetoId, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Projetos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
