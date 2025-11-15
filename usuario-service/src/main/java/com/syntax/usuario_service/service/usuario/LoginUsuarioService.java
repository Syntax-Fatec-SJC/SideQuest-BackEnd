package com.syntax.usuario_service.service.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.syntax.usuario_service.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.LoginDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.repositorio.UsuarioRepositorio;

@Service
public class LoginUsuarioService {
    
    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario realizarLogin(LoginDTO dto) {
        Optional<Usuario> usuario = repositorio.findByEmail(dto.getEmail());

        if (usuario.isPresent()) {
            Usuario usuarioExistente = usuario.get();

            if (passwordEncoder.matches(dto.getSenha(), usuarioExistente.getSenha())) {
                return usuarioExistente;
            }
        }

        throw new CredenciaisInvalidasException("Credenciais Inválidas. Verifique seu e-mail e senha.");
    }
}
