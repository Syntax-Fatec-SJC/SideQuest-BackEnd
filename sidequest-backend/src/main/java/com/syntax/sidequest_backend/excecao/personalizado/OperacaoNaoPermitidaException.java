package com.syntax.sidequest_backend.excecao.personalizado;

/**
 * Exceção lançada quando uma operação não é permitida devido a regras de negócio.
 * 
 * Exemplos de uso:
 * - Tentativa de remover o criador de um projeto
 * - Tentativa de adicionar um membro já existente
 * - Tentativa de remover um membro que não faz parte do projeto
 * 
 * @author SideQuest Team
 */
public class OperacaoNaoPermitidaException extends RuntimeException {
    
    public OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }

    public OperacaoNaoPermitidaException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
