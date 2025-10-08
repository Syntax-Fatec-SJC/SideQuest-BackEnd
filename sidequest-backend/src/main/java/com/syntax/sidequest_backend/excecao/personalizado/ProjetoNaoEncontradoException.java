package com.syntax.sidequest_backend.excecao.personalizado;

/**
 * Exceção lançada quando um projeto não é encontrado no sistema.
 * 
 * @author SideQuest Team
 */
public class ProjetoNaoEncontradoException extends RuntimeException {
    
    public ProjetoNaoEncontradoException(String projetoId) {
        super("Projeto não encontrado com ID: " + projetoId);
    }

    public ProjetoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
