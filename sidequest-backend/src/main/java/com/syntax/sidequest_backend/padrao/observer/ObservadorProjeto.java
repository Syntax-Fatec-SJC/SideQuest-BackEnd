package com.syntax.sidequest_backend.padrao.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.syntax.sidequest_backend.modelo.entidade.Projeto;

@Component
public class ObservadorProjeto implements Observador<Projeto> {
    
    private static final Logger logger = LoggerFactory.getLogger(ObservadorProjeto.class);
    
    @Override
    public void atualizar(String evento, Projeto projeto) {
        switch (evento) {
            case "PROJETO_CRIADO" -> logger.info("Novo projeto criado: {}", projeto.getNome());
            case "PROJETO_ATUALIZADO" -> logger.info("Projeto atualizado: {}", projeto.getNome());
            case "MEMBRO_ADICIONADO" -> logger.info("Membro adicionado ao projeto: {}", projeto.getNome());
            case "MEMBRO_REMOVIDO" -> logger.info("Membro removido do projeto: {}", projeto.getNome());
            default -> logger.info("Evento no projeto {}: {}", projeto.getNome(), evento);
        }
    }
}
