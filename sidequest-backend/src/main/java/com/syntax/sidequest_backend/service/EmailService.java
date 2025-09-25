package com.syntax.sidequest_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    public void enviarEmailResetSenha(String email, String token) {
        String linkReset = frontendUrl + "/reset-password?token=" + token;
        
        logger.info("===== EMAIL DE RESET DE SENHA =====");
        logger.info("Para: {}", email);
        logger.info("Link para redefinir senha: {}", linkReset);
        logger.info("Token expira em 1 hora");
        logger.info("===================================");
    }

    public void enviarEmailBoasVindas(String email, String nome) {
        logger.info("===== EMAIL DE BOAS-VINDAS =====");
        logger.info("Para: {} ({})", email, nome);
        logger.info("Cadastro realizado com sucesso");
        logger.info("===============================");
    }
}
