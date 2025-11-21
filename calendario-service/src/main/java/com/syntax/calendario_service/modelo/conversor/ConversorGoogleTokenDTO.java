package com.syntax.calendario_service.modelo.conversor;

import com.syntax.calendario_service.modelo.dto.GoogleTokenDTO;
import com.syntax.calendario_service.modelo.entidade.GoogleToken;
import org.springframework.stereotype.Component;

@Component
public class ConversorGoogleTokenDTO {
    public GoogleTokenDTO paraDTO(GoogleToken entidade) {
        GoogleTokenDTO dto = new GoogleTokenDTO();
        dto.setUsuarioId(entidade.getUsuarioId());
        dto.setConectado(entidade.getAccessToken() != null && !entidade.getAccessToken().isBlank());
        dto.setTemRefreshToken(entidade.getRefreshToken() != null && !entidade.getRefreshToken().isBlank());
        return dto;
    }
}
