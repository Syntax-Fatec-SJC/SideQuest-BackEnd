package com.syntax.sidequest_backend.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.AtualizarUsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Transactional
@Service
public class AtualizarUsuarioService {

    @Autowired
    private UsuarioRepositorio repositorio;

    public Usuario atualizarUsuario(Usuario usuario, AtualizarUsuarioDTO dto){
        usuario.setNome(dto.getNome());

        return repositorio.save(usuario);
    }
}
