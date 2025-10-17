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

import jakarta.validation.Valid;

@RestController
public class LoginUsuarioControle {

    @Autowired
    private LoginUsuarioService servicoLoginUsuarioService;

    @Autowired JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        Usuario usuario = servicoLoginUsuarioService.realizarLogin(dto);
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getId());
        LoginResponseDTO resposta = ConversorUsuario.converterLogin(usuario, token);

        return ResponseEntity.ok(resposta);
    }
}
