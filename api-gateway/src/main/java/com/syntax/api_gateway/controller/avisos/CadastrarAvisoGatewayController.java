package com.syntax.api_gateway.controller.avisos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.avisos.CadastrarAvisoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller para cadastrar avisos via Gateway
 */
@RestController
@RequestMapping("/cadastrar/avisos")
public class CadastrarAvisoGatewayController {

    @Autowired
    private CadastrarAvisoService cadastrarAvisoService;

    @PostMapping
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> cadastrar(@RequestBody Object body, HttpServletRequest request) {
        return cadastrarAvisoService.cadastrar(request.getRequestURI(), body, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Avisos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
