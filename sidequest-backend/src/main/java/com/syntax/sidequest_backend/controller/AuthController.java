package com.syntax.sidequest_backend.controller;

import com.syntax.sidequest_backend.modelo.dto.AuthResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.CadastroUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginUsuarioDTO;
import com.syntax.sidequest_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthResponseDTO> cadastrar(@Valid @RequestBody CadastroUsuarioDTO cadastroDTO) {
        try {
            AuthResponseDTO response = usuarioService.cadastrarUsuario(cadastroDTO);
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
     * Endpoint para testar se a API está funcionando
     * GET /api/auth/status
     */
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API de autenticação funcionando!");
    }
}
