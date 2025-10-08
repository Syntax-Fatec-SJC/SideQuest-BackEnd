package com.syntax.sidequest_backend.padrao.decorator;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public class UsuarioBasico implements ComponenteUsuario {
    
    @Override
    public Usuario processar(Usuario usuario) {
        return usuario;
    }
}
