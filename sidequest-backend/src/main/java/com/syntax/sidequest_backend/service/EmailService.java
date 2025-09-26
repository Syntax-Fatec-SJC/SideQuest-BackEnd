package com.syntax.sidequest_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired(required = false)
    private JavaMailSender emailSender;

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public void enviarEmailResetSenha(String email, String token) {
        String linkReset = frontendUrl + "/reset-password?token=" + token;
        
        // Se o emailSender estiver configurado, tenta enviar email real
        if (emailSender != null && fromEmail != null && !fromEmail.isEmpty()) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(email);
                message.setSubject("SideQuest - Redefinir Senha");
                message.setText(String.format(
                    "Olá!\n\n" +
                    "Você solicitou a redefinição de sua senha no SideQuest.\n\n" +
                    "Clique no link abaixo para redefinir sua senha:\n" +
                    "%s\n\n" +
                    "Este link expira em 1 hora.\n\n" +
                    "Se você não solicitou esta redefinição, ignore este email.\n\n" +
                    "Atenciosamente,\n" +
                    "Equipe SideQuest",
                    linkReset
                ));

                emailSender.send(message);
                logger.info("Email de reset de senha enviado com sucesso para: {}", email);
                return;
                
            } catch (Exception e) {
                logger.error("Erro ao enviar email de reset de senha para {}: {}", email, e.getMessage());
            }
        }
        
        // Fallback: Log no console para desenvolvimento
        logger.info("===== EMAIL DE RESET DE SENHA (MODO DESENVOLVIMENTO) =====");
        logger.info("Para: {}", email);
        logger.info("Link para redefinir senha: {}", linkReset);
        logger.info("Token expira em 1 hora");
        logger.info("===========================================================");
    }

    public void enviarEmailBoasVindas(String email, String nome) {
        // Se o emailSender estiver configurado, tenta enviar email real
        if (emailSender != null && fromEmail != null && !fromEmail.isEmpty()) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(email);
                message.setSubject("Bem-vindo ao SideQuest!");
                message.setText(String.format(
                    "Olá %s!\n\n" +
                    "Seja bem-vindo ao SideQuest!\n\n" +
                    "Sua conta foi criada com sucesso. Agora você pode gerenciar seus projetos e tarefas de forma eficiente.\n\n" +
                    "Acesse: %s\n\n" +
                    "Atenciosamente,\n" +
                    "Equipe SideQuest",
                    nome, frontendUrl
                ));

                emailSender.send(message);
                logger.info("Email de boas-vindas enviado com sucesso para: {} ({})", email, nome);
                return;
                
            } catch (Exception e) {
                logger.error("Erro ao enviar email de boas-vindas para {}: {}", email, e.getMessage());
            }
        }
        
        // Fallback: Log no console
        logger.info("===== EMAIL DE BOAS-VINDAS (MODO DESENVOLVIMENTO) =====");
        logger.info("Para: {} ({})", email, nome);
        logger.info("Cadastro realizado com sucesso");
        logger.info("========================================================");
    }
}
