package com.syntax.sidequest_backend.padrao.strategy.validacao;

import org.springframework.stereotype.Component;

import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;

@Component
public class ValidacaoUsuario implements ValidacaoStrategy<UsuarioDTO> {
    
    @Override
    public void validar(UsuarioDTO usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        if (!usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
        }
        
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }
}
