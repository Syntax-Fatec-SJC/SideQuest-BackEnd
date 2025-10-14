package com.syntax.sidequest_backend.service.usuario;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Transactional
@Service
public class BuscarUsuarioService {
    
    @Autowired
    private UsuarioRepositorio repositorio;

    public Usuario buscarId(String id){
        Optional<Usuario> usuario = repositorio.findById(id);
        if(!usuario.isPresent()){
            throw new NoSuchElementException(
                "Usuário inexistente na base de dados");
        }
        return usuario.get();
    }

}
