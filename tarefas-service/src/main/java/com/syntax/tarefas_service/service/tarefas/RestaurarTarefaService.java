package com.syntax.tarefas_service.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.modelo.conversor.ConversorTarefaDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

@Service
public class RestaurarTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    public TarefaDTO executar(TarefaDTO dto) {
        if (dto.getId() == null || dto.getNome() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da tarefa inválidos para restauração");
        }

        // Converte DTO para entidade
        Tarefa tarefa = new ConversorTarefaDTO().converter(dto);

        // Salva no banco
        Tarefa salva = tarefaRepositorio.save(tarefa);

        // Retorna DTO atualizado
        return com.syntax.tarefas_service.modelo.conversor.ConversorTarefa.converter(salva);
    }
}
