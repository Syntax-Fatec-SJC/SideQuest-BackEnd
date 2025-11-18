package com.syntax.usuario_service.modelo.conversor;

import com.syntax.usuario_service.modelo.dto.usuarioDTO.CadastrarUsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;

public class ConversorUsuarioDTO {
    
    public static Usuario converter(CadastrarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }
}
