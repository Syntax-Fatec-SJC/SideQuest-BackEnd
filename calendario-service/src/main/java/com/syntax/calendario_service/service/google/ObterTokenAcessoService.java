package com.syntax.calendario_service.service.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.syntax.calendario_service.modelo.conversor.ConversorGoogleToken;
import com.syntax.calendario_service.modelo.entidade.GoogleToken;
import com.syntax.calendario_service.repositorio.GoogleTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ObterTokenAcessoService {

    private static final Logger logger = LoggerFactory.getLogger(ObterTokenAcessoService.class);

    @Autowired
    private GoogleAuthorizationCodeFlow googleFlow;

    @Autowired
    private GoogleTokenRepository tokenRepository;

    @Autowired
    private ConversorGoogleToken conversorGoogleToken;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    public void execute(String code, Long usuarioId) {
        try {
            GoogleTokenResponse tokenResponse = googleFlow
                    .newTokenRequest(code).setRedirectUri(redirectUri).execute();

            GoogleToken tokenExistente = tokenRepository.findByUsuarioId(usuarioId).orElse(new GoogleToken());
            GoogleToken tokenAtualizado = conversorGoogleToken.paraEntidade(tokenResponse, tokenExistente, usuarioId);

            tokenRepository.save(tokenAtualizado);
            logger.info("Tokens do Google salvos para o usuário {}", usuarioId);
        } catch (IOException e) {
            logger.error("Erro ao trocar código por token para o usuário {}: {}", usuarioId, e.getMessage());
            throw new RuntimeException("Falha na autenticação com o Google", e);
        }
    }
}