package com.syntax.projetos_service.excecao;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.excecao.personalizado.RecursoNaoEncontradoException;
import com.syntax.projetos_service.modelo.dto.RespostaDTO.ErroRespostaDTO;

/**
 * Manipulador global de exceções da aplicação
 */
@ControllerAdvice
public class ManipuladorGlobal {

    /**
     * Trata erros de validação de campos
     * Mantém o formato de Map para erros de validação múltiplos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata exceções customizadas de recurso não encontrado
     */
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.NOT_FOUND.value(),
            "Recurso Não Encontrado",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /**
     * Trata exceções de elemento não encontrado
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroRespostaDTO> handleNoSuchElement(NoSuchElementException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.NOT_FOUND.value(),
            "Elemento Não Encontrado",
            ex.getMessage() != null ? ex.getMessage() : "O elemento solicitado não foi encontrado"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /**
     * Trata exceções de argumento ilegal
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroRespostaDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.BAD_REQUEST.value(),
            "Argumento Inválido",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * Trata exceções de status HTTP customizadas
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroRespostaDTO> handleResponseStatus(ResponseStatusException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            ex.getStatusCode().value(),
            ex.getReason() != null ? ex.getReason() : "Erro no Processamento",
            ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(erro);
    }

    /**
     * Trata exceções genéricas não tratadas especificamente
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroRespostaDTO> handleGenericException(Exception ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno do Servidor",
            "Ocorreu um erro inesperado. Tente novamente mais tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
