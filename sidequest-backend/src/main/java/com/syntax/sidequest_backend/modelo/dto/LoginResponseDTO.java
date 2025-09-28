package com.syntax.sidequest_backend.modelo.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String mensagem;

    public LoginResponseDTO(String id, String nome, String email){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.mensagem = "Login realizado com sucesso";
    }
}
