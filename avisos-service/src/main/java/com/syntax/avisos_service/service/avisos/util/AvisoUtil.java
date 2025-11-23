package com.syntax.avisos_service.service.avisos.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.syntax.avisos_service.modelo.entidade.Aviso;

/**
 * Utilitário para operações comuns em avisos
 */
@Component
public class AvisoUtil {
    
    /**
     * Inicializa valores padrão para um novo aviso
     */
    public void inicializarValoresPadrao(Aviso aviso) {
        if (aviso.getData() == null) {
            aviso.setData(new Date());
        }
        
        if (aviso.getVisualizado() == null) {
            aviso.setVisualizado(false);
        }
    }
    
    /**
     * Verifica se o aviso está relacionado a uma tarefa
     */
    public boolean isAvisoTarefa(Aviso aviso) {
        return aviso.getTarefaId() != null && !aviso.getTarefaId().isBlank();
    }
    
    /**
     * Verifica se o aviso está relacionado a um projeto
     */
    public boolean isAvisoProjeto(Aviso aviso) {
        return aviso.getProjetoId() != null && !aviso.getProjetoId().isBlank();
    }
    
    /**
     * Verifica se o aviso já foi visualizado
     */
    public boolean isVisualizado(Aviso aviso) {
        return Boolean.TRUE.equals(aviso.getVisualizado());
    }
    
    /**
     * Formata mensagem de log para criação de aviso
     */
    public String formatarLogCriacao(Aviso aviso) {
        StringBuilder log = new StringBuilder();
        log.append("Aviso criado para usuário ").append(aviso.getUsuarioId());
        
        if (isAvisoTarefa(aviso)) {
            log.append(" | Tarefa: ").append(aviso.getTarefaId());
        }
        
        if (isAvisoProjeto(aviso)) {
            log.append(" | Projeto: ").append(aviso.getProjetoId());
        }
        
        return log.toString();
    }
}
