package com.syntax.sidequest_backend.padrao.factory;

import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;

class ConversorUsuario implements ConversorFactory<Usuario, UsuarioDTO> {
    
    @Override
    public Usuario converterParaEntidade(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }
    
    @Override
    public UsuarioDTO converterParaDTO(Usuario entidade) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setEmail(entidade.getEmail());
        return dto;
    }
}
