package com.syntax.sidequest_backend.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.conversor.ConversorUsuario;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.LoginResponseDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.seguranca.JwtUtil;
import com.syntax.sidequest_backend.service.usuario.LoginUsuarioService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class LoginUsuarioControle {

    @Autowired
    private LoginUsuarioService servicoLoginUsuarioService;

    @Autowired JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto, HttpServletResponse response) {
        Usuario usuario = servicoLoginUsuarioService.realizarLogin(dto);
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getId());
        
        // Criar cookie httpOnly com o token JWT
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);  // Não acessível via JavaScript (protege contra XSS)
        cookie.setSecure(false);   // Temporariamente false para desenvolvimento local (mudar para true em produção com HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 24 horas em segundos
        response.addCookie(cookie);
        
        // Retornar dados do usuário (ainda enviamos o token para compatibilidade)
        LoginResponseDTO resposta = ConversorUsuario.converterLogin(usuario, token);

        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Limpar o cookie do token
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira imediatamente
        response.addCookie(cookie);
        
        return ResponseEntity.ok().build();
    }
}
