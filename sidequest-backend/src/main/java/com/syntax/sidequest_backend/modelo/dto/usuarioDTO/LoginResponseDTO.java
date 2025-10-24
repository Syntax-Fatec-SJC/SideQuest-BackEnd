package com.syntax.sidequest_backend.modelo.dto.usuarioDTO;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String id;
    private String nome;
    private String email;
    private String token;
}
