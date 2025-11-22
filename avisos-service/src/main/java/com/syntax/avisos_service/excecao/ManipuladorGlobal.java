package com.syntax.avisos_service.excecao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.syntax.avisos_service.excecao.personalizado.RecursoNaoEncontradoException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções
 */
@RestControllerAdvice
public class ManipuladorGlobal {
    
    private static final Logger logger = LoggerFactory.getLogger(ManipuladorGlobal.class);
    
    /**
     * Trata exceção de recurso não encontrado
     */
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        logger.error("❌ Recurso não encontrado: {}", ex.getMessage());
        
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
    
    /**
     * Trata erros de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("❌ Erro de validação");
        
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
    
    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("❌ Erro interno: ", ex);
        
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Erro interno do servidor");
        erro.put("detalhes", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
