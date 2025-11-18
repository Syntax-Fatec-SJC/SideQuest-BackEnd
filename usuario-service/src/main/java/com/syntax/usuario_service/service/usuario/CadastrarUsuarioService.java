package com.syntax.usuario_service.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syntax.usuario_service.excecao.personalizado.UsuarioExistenteException;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.repositorio.UsuarioRepositorio;

@Transactional
@Service
public class CadastrarUsuarioService {
    
    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario cadastraUsuario(Usuario usuario) {
        if (repositorio.existsByEmail(usuario.getEmail())) {
            throw new UsuarioExistenteException("Email já está em uso");
        }
        
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return repositorio.save(usuario);
    }
}
