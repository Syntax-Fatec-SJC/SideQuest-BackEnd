package com.syntax.sidequest_backend.padrao.strategy.validacao;

import org.springframework.stereotype.Component;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;

@Component
public class ValidacaoProjeto implements ValidacaoStrategy<ProjetoDTO> {
    
    @Override
    public void validar(ProjetoDTO projeto) {
        if (projeto.getNome() == null || projeto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do projeto não pode ser vazio");
        }
        
        if (projeto.getNome().length() < 3) {
            throw new IllegalArgumentException("Nome do projeto deve ter no mínimo 3 caracteres");
        }
    }
}
