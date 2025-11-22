package com.syntax.avisos_service.excecao.personalizado;

/**
 * Exceção lançada quando um recurso não é encontrado
 */
public class RecursoNaoEncontradoException extends RuntimeException {
    
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
