package com.syntax.sidequest_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

import lombok.Setter;

@Service
public class TarefaService {

    @Autowired
    @Setter

    private TarefaRepositorio repositorio;

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    private Tarefa converterTarefaDTO(TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaDTO.getId());
        tarefa.setNome(tarefaDTO.getNome());
        tarefa.setPrazoFinal(tarefaDTO.getPrazoFinal());
        tarefa.setStatus(tarefaDTO.getStatus());
        tarefa.setComentario(tarefaDTO.getComentario());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setProjetoId(tarefaDTO.getProjetoId());
        tarefa.setAnexos(tarefaDTO.getAnexos());
        tarefa.setUsuarioIds(tarefaDTO.getUsuarioIds());

        return tarefa;
    }

    public List<Tarefa> listarTarefas() {
        return repositorio.findAll();
    }

    public Tarefa buscarTarefa(String id) {
        Optional<Tarefa> tarefaOptional = repositorio.findById(id);
        return tarefaOptional.orElse(null);
    }

    public Tarefa criarTarefa(TarefaDTO tarefaDto) {
        if (tarefaDto.getProjetoId() == null || tarefaDto.getProjetoId().isBlank()) {
            throw new RuntimeException("ProjetoId é obrigatório");
        }
        if (!projetoRepositorio.existsById(tarefaDto.getProjetoId())) {
            throw new RuntimeException("Projeto não encontrado");
        }
        if (tarefaDto.getUsuarioIds() == null) {
            tarefaDto.setUsuarioIds(List.of());
        }
        validarUsuariosDoProjeto(tarefaDto.getUsuarioIds(), tarefaDto.getProjetoId());
        Tarefa tarefa = converterTarefaDTO(tarefaDto);
        return repositorio.save(tarefa);
    }

    public Tarefa atualizarTarefa(TarefaDTO tarefaDto) {
        if (tarefaDto.getId() == null || tarefaDto.getId().isBlank()) {
            throw new RuntimeException("Id da tarefa é obrigatório");
        }
        if (tarefaDto.getProjetoId() != null && !projetoRepositorio.existsById(tarefaDto.getProjetoId())) {
            throw new RuntimeException("Projeto associado não encontrado");
        }
        if (!repositorio.existsById(tarefaDto.getId())) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        if (tarefaDto.getUsuarioIds() == null) {
            tarefaDto.setUsuarioIds(List.of());
        }
        if (tarefaDto.getProjetoId() != null) {
            validarUsuariosDoProjeto(tarefaDto.getUsuarioIds(), tarefaDto.getProjetoId());
        }
        Tarefa tarefa = converterTarefaDTO(tarefaDto);
        return repositorio.save(tarefa);
    }

    public void excluirTarefaPorId(String id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        repositorio.deleteById(id);
    }

    public List<Tarefa> listarPorProjeto(String projetoId) {
        return repositorio.findByProjetoId(projetoId);
    }

    public List<Tarefa> listarPorUsuario(String usuarioId) {
        return repositorio.findByUsuarioIdsContains(usuarioId);
    }

    private void validarUsuariosDoProjeto(List<String> usuarioIds, String projetoId) {
        if (usuarioIds == null || usuarioIds.isEmpty()) return; // lista vazia é sempre válida
        var projetoOpt = projetoRepositorio.findById(projetoId);
        if (projetoOpt.isEmpty()) {
            throw new RuntimeException("Projeto não encontrado para validar usuários");
        }
        var membrosProjeto = projetoOpt.get().getUsuarioIds();
        if (membrosProjeto == null) {
            throw new RuntimeException("Projeto não possui lista de membros configurada");
        }
        for (String uid : usuarioIds) {
            if (!membrosProjeto.contains(uid)) {
                throw new RuntimeException("Usuário " + uid + " não é membro do projeto");
            }
        }
    }

    public Tarefa atualizarResponsaveis(String tarefaId, List<String> usuarioIds) {
        var tarefaOpt = repositorio.findById(tarefaId);
        if (tarefaOpt.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        var tarefa = tarefaOpt.get();
        if (usuarioIds == null) {
            usuarioIds = List.of();
        }
        validarUsuariosDoProjeto(usuarioIds, tarefa.getProjetoId());
        tarefa.setUsuarioIds(usuarioIds);
        return repositorio.save(tarefa);
    }
}
