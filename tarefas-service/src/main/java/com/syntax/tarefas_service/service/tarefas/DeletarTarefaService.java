package com.syntax.tarefas_service.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para deletar tarefa
 */
@Service
public class DeletarTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    public void executar(String id) {
        if (!tarefaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }
        tarefaRepositorio.deleteById(id);
    }
}
