package com.syntax.usuario_service.service.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.repositorio.UsuarioRepositorio;

@Service
public class ListarUsuarioService {
    
    @Autowired
    private UsuarioRepositorio repositorio;

    public List<Usuario> listarTodos() {
        return repositorio.findAll();
    }
}
