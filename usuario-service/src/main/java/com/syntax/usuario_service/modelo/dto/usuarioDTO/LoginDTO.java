package com.syntax.usuario_service.modelo.dto.usuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "E-mail não preenchido ou inexistente")
    @Email(message = "Email deve ter formato válido, ex: nome@dominio.com")
    private String email;

    @NotBlank(message = "Senha não preenchida ou inexistente")
    private String senha;
}
