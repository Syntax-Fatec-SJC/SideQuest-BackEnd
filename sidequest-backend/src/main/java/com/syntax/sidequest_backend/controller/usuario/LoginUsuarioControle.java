package com.syntax.sidequest_backend.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.conversor.ConversorUsuario;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.usuario.LoginUsuarioService;

import jakarta.validation.Valid;

@RestController
public class LoginUsuarioControle {
    @Autowired
    private LoginUsuarioService servicoLoginUsuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginDTO dto){
        Usuario usuario = servicoLoginUsuarioService.realizarLogin(dto);
        UsuarioDTO resposta = ConversorUsuario.converter(usuario);

        return ResponseEntity.ok(resposta);
    }
}
