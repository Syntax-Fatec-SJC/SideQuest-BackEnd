package com.syntax.sidequest_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.sidequest_backend.excecao.personalizado.UsuarioExistenteException;
import com.syntax.sidequest_backend.modelo.dto.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private Usuario converterUsuarioDTO(UsuarioDTO usuarioDTO ) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
        usuario.setSenha(senhaCriptografada);        
   
        return usuario;
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO){
        if (repositorio.existsByEmail(usuarioDTO.getEmail())) {
            throw new UsuarioExistenteException("Email já está em uso");
        }

        Usuario usuario = converterUsuarioDTO(usuarioDTO);
        return repositorio.save(usuario);
    }

    public Usuario atualizarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = converterUsuarioDTO(usuarioDTO);
        Usuario usuarioAtualizado = repositorio.save(usuario);
        return usuarioAtualizado;
    }

    public void excluirUsuario(UsuarioDTO usuarioDTO){
        repositorio.deleteById(usuarioDTO.getId());
    }

    public LoginResponseDTO realizarLogin(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = repositorio.findByEmail(loginDTO.getEmail());
        
        if (usuarioOpt.isEmpty()) {
            throw new CredenciaisInvalidasException("Email ou senha inválidos");
        }
        
        Usuario usuario = usuarioOpt.get();        
        boolean senhaCorreta = passwordEncoder.matches(
            loginDTO.getSenha(), 
            usuario.getSenha()
        );
        
        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException("Email ou senha inválidos");
        }        
        return new LoginResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public List<Usuario> listarTodos() {
        return repositorio.findAll();
    }
}
