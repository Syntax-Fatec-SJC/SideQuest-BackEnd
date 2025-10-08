package com.syntax.sidequest_backend.padrao.decorator;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public class DecoradorValidacao implements ComponenteUsuario {
    
    private final ComponenteUsuario componente;
    
    public DecoradorValidacao(ComponenteUsuario componente) {
        this.componente = componente;
    }
    
    @Override
    public Usuario processar(Usuario usuario) {
        validarDados(usuario);
        return componente.processar(usuario);
    }
    
    private void validarDados(Usuario usuario) {
        if (usuario.getEmail() != null) {
            usuario.setEmail(usuario.getEmail().toLowerCase().trim());
        }
        
        if (usuario.getNome() != null) {
            usuario.setNome(usuario.getNome().trim());
        }
    }
}
