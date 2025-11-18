package com.syntax.api_gateway.controller.tarefas;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.tarefas.AtualizarTarefaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para atualizar tarefas via Gateway
 */
@RestController
@RequestMapping("/atualizar/tarefas")
public class AtualizarTarefaGatewayController {

    @Autowired
    private AtualizarTarefaService atualizarTarefaService;

    @PutMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> atualizar(@RequestBody Object body, HttpServletRequest request) {
        String path = request.getRequestURI();
        return atualizarTarefaService.atualizarCompleto(path, body, request).block();
    }

    @PatchMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponsePatch")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> atualizarParcial(@RequestBody Object body, HttpServletRequest request) {
        String path = request.getRequestURI();
        return atualizarTarefaService.atualizarParcial(path, body, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Tarefas Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    private ResponseEntity<Object> fallbackResponsePatch(Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Tarefas Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
