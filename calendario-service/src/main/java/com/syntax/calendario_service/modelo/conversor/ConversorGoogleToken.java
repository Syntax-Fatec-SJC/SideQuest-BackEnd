package com.syntax.calendario_service.modelo.conversor;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.syntax.calendario_service.modelo.entidade.GoogleToken;

import java.time.Instant;

public class ConversorGoogleToken {
    public GoogleToken paraEntidade(GoogleTokenResponse tokenResponse, GoogleToken tokenExistente, Long usuarioId) {
        tokenExistente.setUsuarioId(usuarioId);
        tokenExistente.setAccessToken(tokenResponse.getAccessToken());
        
        if (tokenResponse.getRefreshToken() != null) {
            tokenExistente.setRefreshToken(tokenResponse.getRefreshToken());
        }
        
        tokenExistente.setExpiresInseconds(tokenResponse.getExpiresInSeconds());
        tokenExistente.setIssuedAt(Instant.now().getEpochSecond());
        
        return tokenExistente;
    }
}
