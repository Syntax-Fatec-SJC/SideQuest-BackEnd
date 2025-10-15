package com.syntax.sidequest_backend.modelo.dto.usuarioDTO;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String id;
    private String nome;
    private String email;
    private String token;
    private String mensagem;

    public LoginResponseDTO(String id, String nome, String email, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
        this.mensagem = "Login realizado com sucesso";
    }
}
