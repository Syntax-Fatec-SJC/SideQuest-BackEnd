package com.syntax.sidequest_backend.modelo.dto;

/**
 * DTO para resposta de cadastro de usuário
 * Retorna apenas uma mensagem de sucesso, sem token de autenticação
 */
public class CadastroResponseDTO {
    private String mensagem;
    private String email;

    public CadastroResponseDTO() {}

    public CadastroResponseDTO(String mensagem, String email) {
        this.mensagem = mensagem;
        this.email = email;
    }

    // Getters e Setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}