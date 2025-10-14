package com.syntax.sidequest_backend.modelo.conversor;

import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.CadastrarUsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
public class ConversorUsuarioDTO {
    
    public static Usuario converter(CadastrarUsuarioDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        return usuario;
    }
}
