package com.syntax.sidequest_backend.excecao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.syntax.sidequest_backend.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.sidequest_backend.excecao.personalizado.OperacaoNaoPermitidaException;
import com.syntax.sidequest_backend.excecao.personalizado.ProjetoNaoEncontradoException;
import com.syntax.sidequest_backend.excecao.personalizado.UsuarioExistenteException;
import com.syntax.sidequest_backend.excecao.personalizado.UsuarioNaoEncontradoException;

/**
 * Manipulador global de exceções da aplicação.
 * 
 * Centraliza o tratamento de exceções e retorna respostas HTTP apropriadas.
 * Segue o padrão de design Exception Handler.
 * 
 * @author SideQuest Team
 */
@ControllerAdvice
public class ManipuladorGlobal {

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

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<Map<String, String>> handleUsuarExistente(UsuarioExistenteException ex){
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); //409 (email ja cadastrado)
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<Map<String, String>> handleCredenciaisInvalidas(CredenciaisInvalidasException ex){
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); //401 (credenciais inválidas)
    }

    @ExceptionHandler(ProjetoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleProjetoNaoEncontrado(ProjetoNaoEncontradoException ex){
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); //404 (projeto não encontrado)
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex){
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); //404 (usuário não encontrado)
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<Map<String, String>> handleOperacaoNaoPermitida(OperacaoNaoPermitidaException ex){
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN); //403 (operação não permitida)
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex){
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500 (erro interno)
    }
}
