package com.syntax.projetos_service.modelo.dto.usuarioDTO;

import lombok.Data;

/**
 * DTO para representar dados de usuário retornados pelo Usuario-Service
 */
@Data
public class UsuarioDTO {
    private String id;
    private String nome;
    private String email;
}
