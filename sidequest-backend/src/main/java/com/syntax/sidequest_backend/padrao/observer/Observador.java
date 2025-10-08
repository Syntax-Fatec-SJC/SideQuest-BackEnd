package com.syntax.sidequest_backend.padrao.observer;

public interface Observador<T> {
    void atualizar(String evento, T dados);
}
