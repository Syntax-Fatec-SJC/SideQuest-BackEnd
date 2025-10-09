package com.syntax.sidequest_backend.modelo.conversor;

import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.CadastrarUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;

@Service
public class ConversorUsuarioDTO {
    
    public static Usuario converter(UsuarioDTO dto){
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        return usuario;
    } 

    public static Usuario converter(CadastrarUsuarioDTO dto){
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        return usuario;
    }
}
