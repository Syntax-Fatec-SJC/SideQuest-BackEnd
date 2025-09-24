package com.syntax.sidequest_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.config.security.JwtUtil;
import com.syntax.sidequest_backend.modelo.dto.AuthResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.CadastroUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

/**
 * Serviço responsável pela lógica de negócio dos usuários
 * Implementa UserDetailsService para integração com Spring Security
 */
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Método obrigatório do UserDetailsService
     * Carrega usuário pelo email para autenticação
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha() != null ? usuario.getSenha() : "")
                .authorities(new ArrayList<>()) // Por enquanto sem roles específicas
                .build();
    }

    /**
     * Cadastra um novo usuário no sistema
     */
    public AuthResponseDTO cadastrarUsuario(CadastroUsuarioDTO cadastroDTO) {
        // Verifica se email já existe
        if (usuarioRepositorio.findByEmail(cadastroDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já está cadastrado no sistema");
        }

        // Cria novo usuário
        Usuario usuario = new Usuario();
        usuario.setNome(cadastroDTO.getNome());
        usuario.setEmail(cadastroDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroDTO.getSenha()));
        usuario.setProvedor("local");
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());

        // Salva no banco
        usuario = usuarioRepositorio.save(usuario);

        // Gera token JWT
        String token = jwtUtil.gerarToken(usuario.getEmail());

        // Retorna resposta com token e dados do usuário
        return new AuthResponseDTO(token, usuario.getId(), usuario.getNome(),
                                  usuario.getEmail(), usuario.getFotoPerfil(), usuario.getProvedor());
    }

    /**
     * Autentica usuário com email e senha
     */
    public AuthResponseDTO autenticarUsuario(LoginUsuarioDTO loginDTO) {
        // Busca usuário por email
        Usuario usuario = usuarioRepositorio.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        // Verifica se é usuário local (não OAuth2)
        if (!"local".equals(usuario.getProvedor())) {
            throw new RuntimeException("Use login social para esta conta");
        }

        // Verifica senha
        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        // Verifica se conta está ativa
        if (!usuario.isAtivo()) {
            throw new RuntimeException("Conta desativada");
        }

        // Atualiza data de último acesso
        usuario.setDataAtualizacao(LocalDateTime.now());
        usuarioRepositorio.save(usuario);

        // Gera token JWT
        String token = jwtUtil.gerarToken(usuario.getEmail());

        // Retorna resposta com token e dados do usuário
        return new AuthResponseDTO(token, usuario.getId(), usuario.getNome(),
                                  usuario.getEmail(), usuario.getFotoPerfil(), usuario.getProvedor());
    }

    /**
     * Busca ou cria usuário para autenticação OAuth2 (Google)
     */
    public Usuario buscarOuCriarUsuarioOAuth2(String email, String nome, String fotoPerfil, String googleId) {
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(email);

        if (usuarioExistente.isPresent()) {
            // Usuário já existe, atualiza informações se necessário
            Usuario usuario = usuarioExistente.get();
            boolean precisaAtualizar = false;

            if (!nome.equals(usuario.getNome())) {
                usuario.setNome(nome);
                precisaAtualizar = true;
            }

            if (fotoPerfil != null && !fotoPerfil.equals(usuario.getFotoPerfil())) {
                usuario.setFotoPerfil(fotoPerfil);
                precisaAtualizar = true;
            }

            if (googleId != null && !googleId.equals(usuario.getProvedorId())) {
                usuario.setProvedorId(googleId);
                precisaAtualizar = true;
            }

            if (precisaAtualizar) {
                usuario.setDataAtualizacao(LocalDateTime.now());
                return usuarioRepositorio.save(usuario);
            }

            return usuario;
        } else {
            // Cria novo usuário OAuth2
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setFotoPerfil(fotoPerfil);
            novoUsuario.setProvedor("google");
            novoUsuario.setProvedorId(googleId);
            novoUsuario.setAtivo(true);
            novoUsuario.setDataCriacao(LocalDateTime.now());
            novoUsuario.setDataAtualizacao(LocalDateTime.now());

            return usuarioRepositorio.save(novoUsuario);
        }
    }

    /**
     * Busca usuário por email
     */
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    /**
     * Busca usuário por ID
     */
    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepositorio.findById(id);
    }

    /**
     * Converte entidade Usuario para DTO
     */
    public UsuarioDTO converterParaDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getFotoPerfil(),
            usuario.getProvedor(),
            usuario.isAtivo(),
            usuario.getDataCriacao()
        );
    }
}
