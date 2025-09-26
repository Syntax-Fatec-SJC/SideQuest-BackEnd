package com.syntax.sidequest_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.service.UsuarioService;

/**
 * Controller para demonstrar endpoints protegidos
 * Estes endpoints só podem ser acessados por usuários autenticados
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint protegido - retorna informações do usuário autenticado
     * GET /api/usuarios/perfil
     */
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obterPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        var usuario = usuarioService.buscarPorEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UsuarioDTO usuarioDTO = usuarioService.converterParaDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    /**
     * Endpoint protegido de exemplo
     * GET /api/usuarios/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(@AuthenticationPrincipal UserDetails userDetails) {
        var usuario = usuarioService.buscarPorEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Simula dados do dashboard
        var dashboardData = new DashboardResponse(
            "Bem-vindo, " + usuario.getNome() + "!",
            "Este é um endpoint protegido que só usuários autenticados podem acessar.",
            usuario.getProvedor()
        );

        return ResponseEntity.ok(dashboardData);
    }

    /**
     * Classe para resposta do dashboard
     */
    public static class DashboardResponse {
        private String mensagem;
        private String descricao;
        private String tipoLogin;

        public DashboardResponse(String mensagem, String descricao, String tipoLogin) {
            this.mensagem = mensagem;
            this.descricao = descricao;
            this.tipoLogin = tipoLogin;
        }

        // Getters e Setters
        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }

        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }

        public String getTipoLogin() { return tipoLogin; }
        public void setTipoLogin(String tipoLogin) { this.tipoLogin = tipoLogin; }
    }
}
