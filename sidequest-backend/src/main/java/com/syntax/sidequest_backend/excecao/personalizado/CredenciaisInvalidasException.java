package com.syntax.sidequest_backend.excecao.personalizado;

public class CredenciaisInvalidasException extends RuntimeException{
    public CredenciaisInvalidasException(String message){
        super(message); 
    }

    public CredenciaisInvalidasException(String message, Throwable cause){
        super(message, cause);
    }
}
