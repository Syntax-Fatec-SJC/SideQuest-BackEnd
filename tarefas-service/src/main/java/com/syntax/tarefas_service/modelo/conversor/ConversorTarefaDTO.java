package com.syntax.tarefas_service.modelo.conversor;

import java.util.ArrayList;
import java.util.List;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;

/**
 * Conversor de TarefaDTO para Tarefa
 */
public class ConversorTarefaDTO {

    public Tarefa converter(TarefaDTO dto) {
        if (dto == null) {
            return null;
        }

        Tarefa tarefa = new Tarefa();
        tarefa.setId(dto.getId());
        tarefa.setNome(dto.getNome());
        tarefa.setPrazoFinal(dto.getPrazoFinal());
        tarefa.setStatus(dto.getStatus());
        tarefa.setComentario(dto.getComentario());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setProjetoId(dto.getProjetoId());
        tarefa.setAnexos(copiarLista(dto.getAnexos()));
        tarefa.setUsuarioIds(copiarLista(dto.getUsuarioIds()));
        return tarefa;
    }

    private static List<String> copiarLista(List<String> origem) {
        if (origem == null || origem.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(origem);
    }
}
