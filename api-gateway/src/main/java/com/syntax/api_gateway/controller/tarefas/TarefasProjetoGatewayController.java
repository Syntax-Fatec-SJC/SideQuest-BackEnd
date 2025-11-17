package com.syntax.api_gateway.controller.tarefas;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.tarefas.TarefasProjetoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller para listar tarefas de um projeto via Gateway
 */
@RestController
@RequestMapping("/projetos")
public class TarefasProjetoGatewayController {

    @Autowired
    private TarefasProjetoService tarefasProjetoService;

    @GetMapping("/{projetoId}/tarefas")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> listarTarefasDoProjeto(
            @PathVariable String projetoId,
            HttpServletRequest request) {
        return tarefasProjetoService.listarTarefasDoProjeto(projetoId, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(String projetoId, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Tarefas Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
