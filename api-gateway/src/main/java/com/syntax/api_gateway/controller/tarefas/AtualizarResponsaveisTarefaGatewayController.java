package com.syntax.api_gateway.controller.tarefas;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.tarefas.AtualizarResponsaveisTarefaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para atualizar responsáveis de tarefas via Gateway
 */
@RestController
@RequestMapping("/tarefas")
public class AtualizarResponsaveisTarefaGatewayController {

    @Autowired
    private AtualizarResponsaveisTarefaService atualizarResponsaveisTarefaService;

    @PatchMapping("/{id}/responsaveis")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> atualizarResponsaveis(
            @PathVariable String id,
            @RequestBody Object body, 
            HttpServletRequest request) {
        return atualizarResponsaveisTarefaService.atualizarResponsaveis(id, body, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(String id, Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Tarefas Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
