package com.syntax.tarefas_service.service.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para listar tarefas por projeto
 */
@Service
public class ListarPorProjetoService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    public List<TarefaDTO> executar(String projetoId) {
        return ConversorTarefa.converter(tarefaRepositorio.findByProjetoId(projetoId));
    }
}
