package com.syntax.sidequest_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.dto.RedefinirSenhaDTO;
import com.syntax.sidequest_backend.dto.SolicitarResetSenhaDTO;
import com.syntax.sidequest_backend.modelo.dto.AuthResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.CadastroResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.CadastroUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginUsuarioDTO;
import com.syntax.sidequest_backend.service.UsuarioService;

import jakarta.validation.Valid;

/**
 * Controller responsável pelos endpoints de autenticação
 * Gerencia cadastro, login local e redirecionamento para OAuth2
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para cadastro de novos usuários
     * POST /api/auth/cadastro
     */
    @PostMapping("/cadastro")
    public ResponseEntity<CadastroResponseDTO> cadastrar(@Valid @RequestBody CadastroUsuarioDTO cadastroDTO) {
        try {
            CadastroResponseDTO response = usuarioService.cadastrarUsuario(cadastroDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para login com email e senha
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginUsuarioDTO loginDTO) {
        try {
            AuthResponseDTO response = usuarioService.autenticarUsuario(loginDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para iniciar login com Google
     * GET /api/auth/google
     * Redireciona para a URL de autorização do Google
     */
    @GetMapping("/google")
    public ResponseEntity<String> loginGoogle() {
        return ResponseEntity.ok("Redirecionando para /oauth2/authorization/google");
    }

    /**
     * Endpoint para solicitar reset de senha
     * POST /api/auth/solicitar-reset-senha
     */
    @PostMapping("/solicitar-reset-senha")
    public ResponseEntity<String> solicitarResetSenha(@Valid @RequestBody SolicitarResetSenhaDTO dto) {
        try {
            usuarioService.solicitarResetSenha(dto.getEmail());
            return ResponseEntity.ok("Se o email existir, um link de reset será enviado.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para redefinir senha com token
     * POST /api/auth/redefinir-senha
     */
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@Valid @RequestBody RedefinirSenhaDTO dto) {
        try {
            usuarioService.redefinirSenha(dto.getToken(), dto.getNovaSenha());
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para testar se a API está funcionando
     * GET /api/auth/status
     */
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API de autenticação funcionando!");
    }
}
