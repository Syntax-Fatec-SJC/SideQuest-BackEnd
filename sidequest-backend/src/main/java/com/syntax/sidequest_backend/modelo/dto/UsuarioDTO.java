package com.syntax.sidequest_backend.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para retornar informações do usuário
 * Usado em respostas que não incluem dados sensíveis como senha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String id;
    private String nome;
    private String email;
    private String fotoPerfil;
    private String provedor;
    private boolean ativo;
    private LocalDateTime dataCriacao;
}
