package com.syntax.sidequest_backend.modelo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para cadastro de novos usuários
 * Contém validações para garantir dados corretos
 */
@Data
public class CadastroUsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;
}
