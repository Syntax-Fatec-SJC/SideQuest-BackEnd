package com.syntax.sidequest_backend.excecao;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.syntax.sidequest_backend.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.sidequest_backend.excecao.personalizado.UsuarioExistenteException;
import com.syntax.sidequest_backend.modelo.dto.RespostaDTO.ErroRespostaDTO;

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

    @ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErroRespostaDTO> buscarEntidades(NoSuchElementException ex) {
		ErroRespostaDTO erroResposta = new ErroRespostaDTO("Falha no processamento dos dados ou elemento não encontrado na base de dados", ex.getMessage());
		ResponseEntity<ErroRespostaDTO> resposta = new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
		return resposta;
	}
}
