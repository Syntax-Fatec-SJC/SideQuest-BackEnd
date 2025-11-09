package com.syntax.usuario_service.modelo.conversor;

import com.syntax.usuario_service.modelo.dto.usuarioDTO.LoginResponseDTO;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;

public class ConversorUsuario {
    
    public static UsuarioDTO converter(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    public static LoginResponseDTO converterLogin(Usuario usuario, String token) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setToken(token);
        return dto;
    }
}
