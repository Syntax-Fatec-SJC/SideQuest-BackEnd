package com.syntax.usuario_service.excecao.personalizado;

public class CredenciaisInvalidasException extends RuntimeException {
    
    public CredenciaisInvalidasException(String message) {
        super(message);
    }

    public CredenciaisInvalidasException(String message, Throwable cause) {
        super(message, cause);
    }
}
