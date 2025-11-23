package com.syntax.api_gateway.excecao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

/**
 * Manipulador global de exceções para o API Gateway
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula erros de autenticação
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, Object> errorDetails = buildErrorResponse(
            HttpStatus.UNAUTHORIZED,
            "Erro de Autenticação",
            "Credenciais inválidas ou token expirado",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    /**
     * Manipula erros de autorização
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> errorDetails = buildErrorResponse(
            HttpStatus.FORBIDDEN,
            "Acesso Negado",
            "Você não tem permissão para acessar este recurso",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }

    /**
     * Manipula erros do Circuit Breaker
     */
    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<Map<String, Object>> handleCallNotPermittedException(CallNotPermittedException ex) {
        Map<String, Object> errorDetails = buildErrorResponse(
            HttpStatus.SERVICE_UNAVAILABLE,
            "Serviço Temporariamente Indisponível",
            "O serviço está temporariamente fora do ar. Circuit breaker ativado.",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDetails);
    }

    /**
     * Manipula erros de Rate Limit
     */
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Map<String, Object>> handleRequestNotPermitted(RequestNotPermitted ex) {
        Map<String, Object> errorDetails = buildErrorResponse(
            HttpStatus.TOO_MANY_REQUESTS,
            "Limite de Requisições Excedido",
            "Você excedeu o número máximo de requisições permitidas. Tente novamente mais tarde.",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorDetails);
    }

    /**
     * Manipula erros de resposta do WebClient
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, Object>> handleWebClientResponseException(WebClientResponseException ex) {
        Map<String, Object> errorDetails = buildErrorResponse(
            HttpStatus.valueOf(ex.getStatusCode().value()),
            "Erro na Comunicação com Microserviço",
            "Ocorreu um erro ao comunicar com o microserviço",
            ex.getResponseBodyAsString()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(errorDetails);
    }

    /**
     * Manipula exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorDetails = buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Erro Interno do Servidor",
            "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    /**
     * Constrói a resposta de erro padronizada
     */
    private Map<String, Object> buildErrorResponse(HttpStatus status, String erro, String mensagem, String detalhes) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("erro", erro);
        errorDetails.put("mensagem", mensagem);
        errorDetails.put("detalhes", detalhes);
        return errorDetails;
    }
}
