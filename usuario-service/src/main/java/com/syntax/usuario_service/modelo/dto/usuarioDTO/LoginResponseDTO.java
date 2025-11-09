package com.syntax.usuario_service.modelo.dto.usuarioDTO;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String token;
}
