package com.syntax.tarefas_service.service.tarefas.listarTarefas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para listar as próximas entregas de tarefas do usuário
 * Retorna tarefas não concluídas com prazo definido, ordenadas por data de entrega
 */
@Service
public class ListarProximasEntregasService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    /**
     * Busca as próximas entregas de tarefas para um usuário
     * 
     * @param usuarioId ID do usuário
     * @return Lista com até 10 tarefas não concluídas ordenadas por prazo mais próximo
     */
    public List<TarefaDTO> executar(String usuarioId) {
        // Busca todas as tarefas do usuário
        List<Tarefa> tarefas = tarefaRepositorio.findByUsuarioIdsContaining(usuarioId);

        // Filtra e ordena as tarefas:
        // 1. Deve ter prazo final definido
        // 2. Não pode estar concluída
        // 3. Ordena por prazo final (mais próximo primeiro)
        // 4. Limita a 10 tarefas
        List<Tarefa> proximasEntregas = tarefas.stream()
            .filter(t -> t.getPrazoFinal() != null)
            .filter(t -> !isStatusConcluido(t.getStatus()))
            .sorted((t1, t2) -> t1.getPrazoFinal().compareTo(t2.getPrazoFinal()))
            .limit(10)
            .collect(Collectors.toList());
        
        return ConversorTarefa.converter(proximasEntregas);
    }
    
    /**
     * Verifica se o status da tarefa é concluído
     * 
     * @param status Status da tarefa
     * @return true se concluída, false caso contrário
     */
    private boolean isStatusConcluido(String status) {
        if (status == null) {
            return false;
        }
        // Os estados possíveis são: PENDENTE, DESENVOLVIMENTO, CONCLUIDO
        return status.equalsIgnoreCase("CONCLUIDO") || 
               status.equalsIgnoreCase("Concluído") ||
               status.equalsIgnoreCase("CONCLUÍDA");
    }
}
