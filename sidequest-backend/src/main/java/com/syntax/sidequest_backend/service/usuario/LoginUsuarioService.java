package com.syntax.sidequest_backend.service.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.LoginDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Service
public class LoginUsuarioService {
    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public Usuario realizarLogin (LoginDTO dto){
        Optional<Usuario> usuario = repositorio.findByEmail(dto.getEmail());

        if(usuario.isPresent()){
            Usuario usuarioExistente = usuario.get();

            if(passwordEncoder.matches(dto.getSenha(), usuarioExistente.getSenha())){
                return usuarioExistente;
            }
        }

        throw new CredenciaisInvalidasException("Credenciais Inválidas. Verifique seu e-mail e senha.");
    }
}
