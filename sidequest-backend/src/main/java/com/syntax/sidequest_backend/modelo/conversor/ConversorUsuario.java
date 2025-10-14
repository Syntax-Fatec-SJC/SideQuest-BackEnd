package com.syntax.sidequest_backend.modelo.conversor;

import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public class ConversorUsuario {
    public static UsuarioDTO converter(Usuario usuario){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());

        return dto;
    }
}
