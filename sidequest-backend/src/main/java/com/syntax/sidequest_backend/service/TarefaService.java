package com.syntax.sidequest_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.padrao.factory.ConversorFactory;
import com.syntax.sidequest_backend.padrao.strategy.validacao.ValidacaoTarefa;
import com.syntax.sidequest_backend.padrao.template.ServicoTemplate;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;
import com.syntax.sidequest_backend.service.interfaces.ITarefaService;

@Service
public class TarefaService extends ServicoTemplate<Tarefa, TarefaDTO> implements ITarefaService {

    private final TarefaRepositorio repositorio;
    private final ProjetoRepositorio projetoRepositorio;
    private final ConversorFactory<Tarefa, TarefaDTO> conversor;
    private final ValidacaoTarefa validacao;

    public TarefaService(
            TarefaRepositorio repositorio,
            ProjetoRepositorio projetoRepositorio,
            ValidacaoTarefa validacao) {
        this.repositorio = repositorio;
        this.projetoRepositorio = projetoRepositorio;
        this.conversor = ConversorFactory.criarConversorTarefa();
        this.validacao = validacao;
    }

    @Override
    public List<Tarefa> listarPorProjeto(String projetoId) {
        return repositorio.findByProjetoId(projetoId);
    }

    @Override
    public List<Tarefa> listarPorUsuario(String usuarioId) {
        return repositorio.findByUsuarioIdsContains(usuarioId);
    }

    @Override
    public Tarefa atualizarResponsaveis(String tarefaId, List<String> usuarioIds) {
        Tarefa tarefa = repositorio.findById(tarefaId)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        
        if (usuarioIds == null) {
            usuarioIds = List.of();
        }
        
        validarUsuariosDoProjeto(usuarioIds, tarefa.getProjetoId());
        tarefa.setUsuarioIds(usuarioIds);
        return repositorio.save(tarefa);
    }

    @Override
    protected void validarAntesDeGravar(TarefaDTO dto) {
        validacao.validar(dto);
        
        if (!projetoRepositorio.existsById(dto.getProjetoId())) {
            throw new RuntimeException("Projeto não encontrado");
        }
        
        if (dto.getUsuarioIds() != null && !dto.getUsuarioIds().isEmpty()) {
            validarUsuariosDoProjeto(dto.getUsuarioIds(), dto.getProjetoId());
        }
    }

    @Override
    protected Tarefa converterParaEntidade(TarefaDTO dto) {
        return conversor.converterParaEntidade(dto);
    }

    @Override
    protected Tarefa salvar(Tarefa entidade) {
        return repositorio.save(entidade);
    }

    @Override
    protected void validarAntesDeExcluir(String id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
    }

    @Override
    protected void executarExclusao(String id) {
        repositorio.deleteById(id);
    }

    @Override
    protected List<Tarefa> buscarTodos() {
        return repositorio.findAll();
    }

    private void validarUsuariosDoProjeto(List<String> usuarioIds, String projetoId) {
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            return;
        }
        
        var projeto = projetoRepositorio.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        
        var membrosProjeto = projeto.getUsuarioIds();
        if (membrosProjeto == null) {
            throw new RuntimeException("Projeto não possui membros configurados");
        }
        
        for (String usuarioId : usuarioIds) {
            if (!membrosProjeto.contains(usuarioId)) {
                throw new RuntimeException("Usuário " + usuarioId + " não é membro do projeto");
            }
        }
    }
}
