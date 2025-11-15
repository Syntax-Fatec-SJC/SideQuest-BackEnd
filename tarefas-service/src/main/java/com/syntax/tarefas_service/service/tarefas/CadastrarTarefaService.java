package com.syntax.tarefas_service.service.tarefas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.ProjetosClient;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefaDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para cadastrar nova tarefa
 */
@Service
public class CadastrarTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private ProjetosClient projetosClient;

    public TarefaDTO executar(TarefaDTO dto) {
        if (dto.getProjetoId() == null || dto.getProjetoId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProjetoId é obrigatório");
        }

        // Busca projeto no Projetos-Service
        ProjetoDTO projeto = projetosClient.buscarProjeto(dto.getProjetoId());

        List<String> usuarioIds = normalizarLista(dto.getUsuarioIds());
        validarUsuariosDoProjeto(usuarioIds, projeto);

        Tarefa tarefa = new ConversorTarefaDTO().converter(dto);
        tarefa.setUsuarioIds(usuarioIds);

        Tarefa salva = tarefaRepositorio.save(tarefa);
        return ConversorTarefa.converter(salva);
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

        for (String usuarioId : usuarioIds) {
            if (!membros.contains(usuarioId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Usuário " + usuarioId + " não está vinculado ao projeto informado");
            }
        }
    }
}
