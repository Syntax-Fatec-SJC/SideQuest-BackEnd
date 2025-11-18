package com.syntax.api_gateway.controller.usuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.api_gateway.service.usuario.LoginCadastrarUsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * Controller para login e cadastro de usuários via Gateway
 */
@RestController
@RequestMapping("/usuario")
public class LoginCadastrarUsuarioGatewayController {

    @Autowired
    private LoginCadastrarUsuarioService loginCadastrarUsuarioService;

    @PostMapping("/login")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> login(@RequestBody Object body, HttpServletRequest request) {
        return loginCadastrarUsuarioService.processar(request.getRequestURI(), body, request).block();
    }

    @PostMapping("/cadastrar")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "default")
    @Retry(name = "default")
    public ResponseEntity<Object> cadastrar(@RequestBody Object body, HttpServletRequest request) {
        return loginCadastrarUsuarioService.processar(request.getRequestURI(), body, request).block();
    }

    private ResponseEntity<Object> fallbackResponse(Object body, HttpServletRequest request, Exception e) {
        Map<String, String> error = Map.of(
            "erro", "Usuario Service temporariamente indisponível",
            "mensagem", "Tente novamente em alguns instantes",
            "detalhes", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
