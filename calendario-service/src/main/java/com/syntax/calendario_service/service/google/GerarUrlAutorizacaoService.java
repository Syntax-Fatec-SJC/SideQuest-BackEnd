package com.syntax.calendario_service.service.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GerarUrlAutorizacaoService {

    @Autowired
    private GoogleAuthorizationCodeFlow googleFlow;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    public String execute(String usuarioId) {
        return googleFlow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .setState(usuarioId)
                .build();
    }
}
