package com.syntax.sidequest_backend.padrao.adapter;

import com.syntax.sidequest_backend.modelo.dto.MembroProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public class AdaptadorUsuarioParaMembro {
    
    public static MembroProjetoDTO adaptar(Usuario usuario, boolean ehCriador) {
        if (usuario == null) {
            return null;
        }
        
        return new MembroProjetoDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            ehCriador
        );
    }
}
