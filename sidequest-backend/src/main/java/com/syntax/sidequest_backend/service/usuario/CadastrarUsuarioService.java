package com.syntax.sidequest_backend.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syntax.sidequest_backend.excecao.personalizado.UsuarioExistenteException;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Transactional
@Service
public class CadastrarUsuarioService {
    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public Usuario cadastraUsuario(Usuario usuario){
        if (repositorio.existsByEmail(usuario.getEmail())){
            throw new UsuarioExistenteException("Email já está em uso");
        }
        String senhaCripotografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripotografada);

        return repositorio.save(usuario);
    }
}
