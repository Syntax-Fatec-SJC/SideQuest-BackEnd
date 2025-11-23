package com.syntax.usuario_service.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.usuario_service.modelo.conversor.ConversorUsuario;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.LoginDTO;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.LoginResponseDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.seguranca.JwtUtil;
import com.syntax.usuario_service.service.usuario.LoginUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginUsuarioService servicoLoginUsuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        Usuario usuario = servicoLoginUsuarioService.realizarLogin(dto);
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getId(), usuario.getNome());
        
        LoginResponseDTO resposta = ConversorUsuario.converterLogin(usuario, token);
        return ResponseEntity.ok(resposta);
    }
}
