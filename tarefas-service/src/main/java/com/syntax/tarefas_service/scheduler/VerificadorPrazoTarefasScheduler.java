package com.syntax.tarefas_service.scheduler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.syntax.tarefas_service.client.AvisosClient;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Scheduler responsável por verificar prazos de tarefas
 * e criar avisos automáticos para tarefas que vencem hoje ou já venceram
 */
@Component
public class VerificadorPrazoTarefasScheduler {

    private static final Logger logger = LoggerFactory.getLogger(VerificadorPrazoTarefasScheduler.class);

    private final TarefaRepositorio tarefaRepositorio;
    private final AvisosClient avisosClient;

    public VerificadorPrazoTarefasScheduler(TarefaRepositorio tarefaRepositorio, AvisosClient avisosClient) {
        this.tarefaRepositorio = tarefaRepositorio;
        this.avisosClient = avisosClient;
    }

    /**
     * Executa verificação de prazos diariamente às 8h da manhã
     * cron: segundos minutos horas dia mês dia-da-semana
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void verificarPrazosTarefas() {
        logger.info("🔍 Iniciando verificação de prazos de tarefas...");
        
        try {
            List<Tarefa> todasTarefas = tarefaRepositorio.findAll();
            LocalDate hoje = LocalDate.now();
            
            int tarefasVencendoHoje = 0;
            int tarefasVencidas = 0;

            for (Tarefa tarefa : todasTarefas) {
                // Ignora tarefas já concluídas
                if ("Concluído".equalsIgnoreCase(tarefa.getStatus())) {
                    continue;
                }

                // Ignora tarefas sem prazo definido
                if (tarefa.getPrazoFinal() == null) {
                    continue;
                }

                LocalDate prazoFinal = convertToLocalDate(tarefa.getPrazoFinal());
                
                // Tarefa vence hoje
                if (prazoFinal.isEqual(hoje)) {
                    logger.info("⚠️ Tarefa '{}' vence hoje", tarefa.getId());
                    criarAvisosPrazo(tarefa, true);
                    tarefasVencendoHoje++;
                }
                // Tarefa já passou do prazo
                else if (prazoFinal.isBefore(hoje)) {
                    logger.info("🚨 Tarefa '{}' passou do prazo", tarefa.getId());
                    criarAvisosPrazo(tarefa, false);
                    tarefasVencidas++;
                }
            }

            logger.info("✅ Verificação concluída: {} tarefas vencendo hoje, {} tarefas vencidas", 
                       tarefasVencendoHoje, tarefasVencidas);
                       
        } catch (Exception e) {
            logger.error("❌ Erro ao verificar prazos de tarefas: {}", e.getMessage(), e);
        }
    }

    /**
     * Cria avisos para todos os usuários responsáveis pela tarefa
     * 
     * @param tarefa Tarefa que precisa de aviso
     * @param venceHoje true se vence hoje, false se já passou do prazo
     */
    private void criarAvisosPrazo(Tarefa tarefa, boolean venceHoje) {
        if (tarefa.getUsuarioIds() == null || tarefa.getUsuarioIds().isEmpty()) {
            logger.warn("⚠️ Tarefa '{}' não possui usuários responsáveis", tarefa.getId());
            return;
        }

        for (String usuarioId : tarefa.getUsuarioIds()) {
            if (venceHoje) {
                avisosClient.criarAvisoTarefaVenceHoje(
                    tarefa.getId(), 
                    tarefa.getProjetoId(), 
                    usuarioId
                );
            } else {
                avisosClient.criarAvisoTarefaPassouPrazo(
                    tarefa.getId(), 
                    tarefa.getProjetoId(), 
                    usuarioId
                );
            }
        }
    }

    /**
     * Converte Date para LocalDate
     */
    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
}
