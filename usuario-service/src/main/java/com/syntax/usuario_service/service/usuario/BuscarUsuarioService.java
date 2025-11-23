package com.syntax.usuario_service.service.usuario;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.repositorio.UsuarioRepositorio;

@Service
public class BuscarUsuarioService {
    
    @Autowired
    private UsuarioRepositorio repositorio;

    public Usuario buscarPorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com ID: " + id));
    }
}
