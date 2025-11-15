package com.syntax.usuario_service.modelo.dto.usuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastrarUsuarioDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido, ex: nome@dominio.com")
    private String email;

    @NotBlank(message = "Senha é obrigatório")
    private String senha;
}
