package com.syntax.api_gateway.modelo.dto;

import java.time.LocalDateTime;

/**
 * DTO para respostas de sucesso padronizadas
 */
public class RespostaDTO<T> {
    
    private LocalDateTime timestamp;
    private Integer status;
    private String mensagem;
    private T dados;

    public RespostaDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public RespostaDTO(Integer status, String mensagem, T dados) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public static <T> RespostaDTO<T> sucesso(T dados) {
        return new RespostaDTO<>(200, "Operação realizada com sucesso", dados);
    }

    public static <T> RespostaDTO<T> sucesso(String mensagem, T dados) {
        return new RespostaDTO<>(200, mensagem, dados);
    }

    public static <T> RespostaDTO<T> criado(T dados) {
        return new RespostaDTO<>(201, "Recurso criado com sucesso", dados);
    }

    public static <T> RespostaDTO<T> criado(String mensagem, T dados) {
        return new RespostaDTO<>(201, mensagem, dados);
    }

    // Getters e Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }
}
