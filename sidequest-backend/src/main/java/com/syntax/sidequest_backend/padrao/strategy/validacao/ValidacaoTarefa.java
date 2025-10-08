package com.syntax.sidequest_backend.padrao.strategy.validacao;

import org.springframework.stereotype.Component;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;

@Component
public class ValidacaoTarefa implements ValidacaoStrategy<TarefaDTO> {
    
    @Override
    public void validar(TarefaDTO tarefa) {
        if (tarefa.getNome() == null || tarefa.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da tarefa não pode ser vazio");
        }
        
        if (tarefa.getProjetoId() == null || tarefa.getProjetoId().isBlank()) {
            throw new IllegalArgumentException("Tarefa deve estar associada a um projeto");
        }
    }
}
