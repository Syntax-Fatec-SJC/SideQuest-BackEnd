package com.syntax.usuario_service.excecao;

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

import com.syntax.usuario_service.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.usuario_service.excecao.personalizado.UsuarioExistenteException;
import com.syntax.usuario_service.modelo.dto.RespostaDTO.ErroRespostaDTO;

/**
 * Manipulador global de exceções para o microserviço de usuários
 */
@ControllerAdvice
public class ManipuladorGlobal {

    /**
     * Trata erros de validação de campos
     * Mantém o formato de Map para erros de validação múltiplos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata exceções de credenciais inválidas (login)
     */
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroRespostaDTO> handleCredenciaisInvalidasException(CredenciaisInvalidasException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.UNAUTHORIZED.value(),
            "Credenciais Inválidas",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    /**
     * Trata exceções de usuário já existente (cadastro)
     */
    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ErroRespostaDTO> handleUsuarioExistenteException(UsuarioExistenteException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.CONFLICT.value(),
            "Usuário Já Existe",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    /**
     * Trata exceções de elemento não encontrado
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroRespostaDTO> handleNoSuchElementException(NoSuchElementException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            HttpStatus.NOT_FOUND.value(),
            "Recurso Não Encontrado",
            ex.getMessage() != null ? ex.getMessage() : "O recurso solicitado não foi encontrado"
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
    public ResponseEntity<ErroRespostaDTO> handleResponseStatusException(ResponseStatusException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
            ex.getStatusCode().value(),
            "Erro no Servidor",
            ex.getReason() != null ? ex.getReason() : "Erro no processamento da requisição"
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
