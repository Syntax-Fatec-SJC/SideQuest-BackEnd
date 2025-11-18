package com.syntax.api_gateway.controller.projetos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.projetos.AtualizarProjetoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para atualizar projetos via Gateway
 */
@RestController
@RequestMapping("/atualizar/projetos")
public class AtualizarProjetoGatewayController {

    @Autowired
    private AtualizarProjetoService atualizarProjetoService;

    @PutMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> atualizar(@RequestBody Object body, HttpServletRequest request) {
        return atualizarProjetoService.atualizar(request.getRequestURI(), body, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Projetos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
