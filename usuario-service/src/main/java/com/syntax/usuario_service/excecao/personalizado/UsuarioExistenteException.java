package com.syntax.usuario_service.excecao.personalizado;

public class UsuarioExistenteException extends RuntimeException {
    
    public UsuarioExistenteException(String message) {
        super(message);
    }

    public UsuarioExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
