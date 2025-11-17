package com.syntax.api_gateway.controller.projetos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.projetos.DeletarProjetoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller para deletar projetos via Gateway
 */
@RestController
@RequestMapping("/excluir/projetos")
public class DeletarProjetoGatewayController {

        @Autowired
    private DeletarProjetoService deletarProjetoService;

    @DeleteMapping("/**")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> deletar(HttpServletRequest request) {
        return deletarProjetoService.deletar(request.getRequestURI(), request).block();
    }

    private ResponseEntity<Object> fallbackResponse(HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Projetos Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
