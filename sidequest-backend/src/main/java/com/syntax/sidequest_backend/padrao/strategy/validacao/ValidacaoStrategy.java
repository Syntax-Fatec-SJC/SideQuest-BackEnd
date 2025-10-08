package com.syntax.sidequest_backend.padrao.strategy.validacao;

public interface ValidacaoStrategy<T> {
    void validar(T objeto);
}
