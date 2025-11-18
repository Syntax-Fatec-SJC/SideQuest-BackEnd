package com.syntax.tarefas_service.service.tarefas.listarTarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para buscar tarefa por ID
 */
@Service
public class ListarPorIdService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    public TarefaDTO executar(String id) {
        Tarefa tarefa = tarefaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
        return ConversorTarefa.converter(tarefa);
    }
}
