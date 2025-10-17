package com.syntax.sidequest_backend.service.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Service
public class ListarUsuarioService {
    @Autowired
    private UsuarioRepositorio repositorio;

    public List<Usuario> listarTodos(){
        return repositorio.findAll();
    }
}
