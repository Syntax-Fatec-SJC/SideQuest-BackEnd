package com.syntax.sidequest_backend.excecao;

import lombok.Getter;

@Getter
public class AppExcecao extends RuntimeException {

    private final String mensagem;

    public AppExcecao(String mensagem) {
        super(mensagem);
        this.mensagem = mensagem;
    }
}
