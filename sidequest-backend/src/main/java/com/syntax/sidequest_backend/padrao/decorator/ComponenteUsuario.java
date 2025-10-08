package com.syntax.sidequest_backend.padrao.decorator;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public interface ComponenteUsuario {
    Usuario processar(Usuario usuario);
}
