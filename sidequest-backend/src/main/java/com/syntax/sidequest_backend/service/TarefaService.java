package com.syntax.sidequest_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

import lombok.Setter;

@Service
public class TarefaService {

    @Autowired
    @Setter

    private TarefaRepositorio repositorio;

    private Tarefa converterTarefaDTO(TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaDTO.getId());
        tarefa.setNome(tarefaDTO.getNome());
        tarefa.setPrazoFinal(tarefaDTO.getPrazoFinal());
        tarefa.setStatus(tarefaDTO.getStatus());
        tarefa.setComentario(tarefaDTO.getComentario());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setProjetoId(tarefaDTO.getProjetoId());
        tarefa.setAnexo(tarefaDTO.getAnexo());
        tarefa.setUsuariosIds(tarefaDTO.getUsuariosIds());

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
        Tarefa tarefa = converterTarefaDTO(tarefaDto);
        Tarefa tarefaSalva = repositorio.save(tarefa);
        return tarefaSalva;
    }

    public Tarefa atualizarTarefa(TarefaDTO tarefaDto) {
        Tarefa tarefa = converterTarefaDTO(tarefaDto);
        Tarefa tarefaAtualizada = repositorio.save(tarefa);
        return tarefaAtualizada;
    }

    public void excluirTarefa(TarefaDTO tarefaDto) {
        repositorio.deleteById(tarefaDto.getId());
    }
}
