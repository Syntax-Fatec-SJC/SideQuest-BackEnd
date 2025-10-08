package com.syntax.sidequest_backend.service.interfaces;

import java.util.List;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;

public interface ITarefaService extends ServicoBase<Tarefa, TarefaDTO> {
    List<Tarefa> listarPorProjeto(String projetoId);
    List<Tarefa> listarPorUsuario(String usuarioId);
    Tarefa atualizarResponsaveis(String tarefaId, List<String> usuarioIds);
}
