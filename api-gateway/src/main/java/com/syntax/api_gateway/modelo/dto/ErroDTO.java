package com.syntax.api_gateway.modelo.dto;

import java.time.LocalDateTime;

/**
 * DTO para respostas de erro padronizadas
 */
public class ErroDTO {
    
    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String detalhes;
    private String path;

    public ErroDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ErroDTO(Integer status, String erro, String mensagem, String detalhes) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

    public ErroDTO(Integer status, String erro, String mensagem, String detalhes, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
        this.path = path;
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

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
