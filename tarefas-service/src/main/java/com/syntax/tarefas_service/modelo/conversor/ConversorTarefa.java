package com.syntax.tarefas_service.modelo.conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;

/**
 * Conversor de Tarefa para TarefaDTO
 */
public class ConversorTarefa {

    public static TarefaDTO converter(Tarefa tarefa) {
        if (tarefa == null) {
            return null;
        }

        TarefaDTO dto = new TarefaDTO();
        dto.setId(tarefa.getId());
        dto.setNome(tarefa.getNome());
        dto.setPrazoFinal(tarefa.getPrazoFinal());
        dto.setStatus(tarefa.getStatus());
        dto.setComentario(tarefa.getComentario());
        dto.setDescricao(tarefa.getDescricao());
        dto.setProjetoId(tarefa.getProjetoId());
        dto.setAnexos(copiarLista(tarefa.getAnexos()));
        dto.setUsuarioIds(copiarLista(tarefa.getUsuarioIds()));
        return dto;
    }

    public static List<TarefaDTO> converter(List<Tarefa> tarefas) {
        if (tarefas == null || tarefas.isEmpty()) {
            return new ArrayList<>();
        }

        return tarefas.stream()
                .filter(Objects::nonNull)
                .map(ConversorTarefa::converter)
                .collect(Collectors.toList());
    }

    private static List<String> copiarLista(List<String> origem) {
        if (origem == null || origem.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(origem);
    }
}
