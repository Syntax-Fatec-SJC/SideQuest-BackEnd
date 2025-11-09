package com.syntax.tarefas_service.service.tarefas.atualizarTarefa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.ProjetosClient;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para atualizar apenas os responsáveis da tarefa
 */
@Service
public class AtualizarResponsaveisTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private ProjetosClient projetosClient;

    public TarefaDTO executar(String tarefaId, List<String> usuarioIds) {
        Tarefa tarefa = tarefaRepositorio.findById(tarefaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        List<String> normalizados = normalizarLista(usuarioIds);
        ProjetoDTO projeto = projetosClient.buscarProjeto(tarefa.getProjetoId());

        validarUsuariosDoProjeto(normalizados, projeto);

        tarefa.setUsuarioIds(normalizados);
        Tarefa salvo = tarefaRepositorio.save(tarefa);
        return ConversorTarefa.converter(salvo);
    }

    private List<String> normalizarLista(List<String> origem) {
        return origem == null ? new ArrayList<>() : new ArrayList<>(origem);
    }

    private void validarUsuariosDoProjeto(List<String> usuarioIds, ProjetoDTO projeto) {
        if (usuarioIds.isEmpty()) {
            return;
        }

        List<String> membros = projeto.getUsuarioIds();
        if (membros == null || membros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projeto não possui membros cadastrados");
        }

        Set<String> membrosSet = new HashSet<>(membros);
        for (String usuarioId : usuarioIds) {
            if (!membrosSet.contains(usuarioId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Usuário " + usuarioId + " não está vinculado ao projeto informado");
            }
        }
    }
}
