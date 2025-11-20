package com.syntax.tarefas_service.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.AvisosClient;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para deletar tarefa
 */
@Service
public class DeletarTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private AvisosClient avisosClient;

    public void executar(String id, String autorId, String autorNome) {
        Tarefa tarefa = tarefaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
        
        // Cria avisos para TODOS os usuários atrelados antes de deletar
        if (autorNome != null && !autorNome.isBlank() && tarefa.getUsuarioIds() != null) {
            for (String usuarioId : tarefa.getUsuarioIds()) {
                avisosClient.criarAvisoTarefaExcluida(
                    tarefa.getId(),
                    tarefa.getProjetoId(),
                    usuarioId,
                    autorId,
                    autorNome
                );
            }
            
            // Cria aviso também para o autor se ele não estiver na lista
            if (autorId != null && !tarefa.getUsuarioIds().contains(autorId)) {
                avisosClient.criarAvisoTarefaExcluida(
                    tarefa.getId(),
                    tarefa.getProjetoId(),
                    autorId,
                    autorId,
                    autorNome
                );
            }
        }
        
        tarefaRepositorio.deleteById(id);
    }
}
