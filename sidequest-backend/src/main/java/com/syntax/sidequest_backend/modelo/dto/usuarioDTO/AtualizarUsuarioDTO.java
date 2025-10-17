package com.syntax.sidequest_backend.modelo.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AtualizarUsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
}
