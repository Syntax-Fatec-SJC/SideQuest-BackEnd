package com.syntax.sidequest_backend.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de autenticação
 * Retorna o token JWT e informações do usuário após login/cadastro
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String tipo = "Bearer";
    private String id;
    private String nome;
    private String email;
    private String fotoPerfil;
    private String provedor;

    public AuthResponseDTO(String token, String id, String nome, String email, String fotoPerfil, String provedor) {
        this.token = token;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
        this.provedor = provedor;
    }
}
