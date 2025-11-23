package com.syntax.usuario_service.modelo.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para atualização de dados do usuário
 */
@Data
public class AtualizarUsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
}
