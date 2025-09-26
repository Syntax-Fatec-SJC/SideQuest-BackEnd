package com.syntax.sidequest_backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.excecao.AppExcecao;
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
        return repositorio.findById(id).orElse(null);
    }

    public Tarefa criarTarefa(TarefaDTO tarefaDto) {
        // DTO já valida campos obrigatórios, então só salva
        Tarefa tarefa = converterTarefaDTO(tarefaDto);
        return repositorio.save(tarefa);
    }

    public Tarefa atualizarTarefa(TarefaDTO tarefaDto) {
        if (tarefaDto.getId() == null || !repositorio.existsById(tarefaDto.getId())) {
            throw new AppExcecao("Tarefa não encontrada para atualização");
        }

        Tarefa tarefa = converterTarefaDTO(tarefaDto);
        return repositorio.save(tarefa);
    }

    public void excluirTarefa(TarefaDTO tarefaDto) {
        if (tarefaDto.getId() == null || !repositorio.existsById(tarefaDto.getId())) {
            throw new AppExcecao("Tarefa não encontrada para exclusão");
        }

        repositorio.deleteById(tarefaDto.getId());
    }
}
