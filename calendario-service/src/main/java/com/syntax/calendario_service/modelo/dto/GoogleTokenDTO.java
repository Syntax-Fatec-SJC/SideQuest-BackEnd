package com.syntax.calendario_service.modelo.dto;

import lombok.Data;

@Data
public class GoogleTokenDTO {
    private Long usuarioId;
    private boolean conectado;
    private boolean temRefreshToken;
}
