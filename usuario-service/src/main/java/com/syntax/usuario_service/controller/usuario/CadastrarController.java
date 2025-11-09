package com.syntax.usuario_service.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.usuario_service.modelo.conversor.ConversorUsuarioDTO;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.CadastrarUsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.service.usuario.CadastrarUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class CadastrarController {
    
    @Autowired
    private CadastrarUsuarioService servicoCadastrarUsuario;

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastro(@Valid @RequestBody CadastrarUsuarioDTO dto) {
        Usuario usuario = ConversorUsuarioDTO.converter(dto);
        servicoCadastrarUsuario.cadastraUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
