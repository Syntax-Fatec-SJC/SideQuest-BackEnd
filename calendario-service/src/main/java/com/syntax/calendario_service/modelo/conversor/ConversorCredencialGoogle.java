package com.syntax.calendario_service.modelo.conversor;

import com.google.auth.oauth2.UserCredentials;
import com.syntax.calendario_service.modelo.entidade.GoogleToken;
import org.springframework.stereotype.Component;

@Component
public class ConversorCredencialGoogle {
    public UserCredentials paraCredencial(GoogleToken token, String clientId, String clientSecret) {
        return UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(token.getRefreshToken())
                .build();
    }
}
