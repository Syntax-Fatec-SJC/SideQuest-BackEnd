package com.syntax.sidequest_backend.excecao.personalizado;

/**
 * Exceção lançada quando um usuário não é encontrado no sistema.
 * 
 * @author SideQuest Team
 */
public class UsuarioNaoEncontradoException extends RuntimeException {
    
    public UsuarioNaoEncontradoException(String usuarioId) {
        super("Usuário não encontrado com ID: " + usuarioId);
    }

    public UsuarioNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
