package com.syntax.sidequest_backend.padrao.factory;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;

class ConversorTarefa implements ConversorFactory<Tarefa, TarefaDTO> {
    
    @Override
    public Tarefa converterParaEntidade(TarefaDTO dto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(dto.getId());
        tarefa.setNome(dto.getNome());
        tarefa.setPrazoFinal(dto.getPrazoFinal());
        tarefa.setStatus(dto.getStatus());
        tarefa.setComentario(dto.getComentario());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setProjetoId(dto.getProjetoId());
        tarefa.setAnexos(dto.getAnexos());
        tarefa.setUsuarioIds(dto.getUsuarioIds());
        return tarefa;
    }
    
    @Override
    public TarefaDTO converterParaDTO(Tarefa entidade) {
        TarefaDTO dto = new TarefaDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setPrazoFinal(entidade.getPrazoFinal());
        dto.setStatus(entidade.getStatus());
        dto.setComentario(entidade.getComentario());
        dto.setDescricao(entidade.getDescricao());
        dto.setProjetoId(entidade.getProjetoId());
        dto.setAnexos(entidade.getAnexos());
        dto.setUsuarioIds(entidade.getUsuarioIds());
        return dto;
    }
}
