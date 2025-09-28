package com.syntax.sidequest_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.syntax.sidequest_backend.modelo.dto.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastrar/usuarios")
    public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuarioCriado = service.criarUsuario(usuarioDTO);
        ResponseEntity<Usuario> resposta = new ResponseEntity<>(usuarioCriado, HttpStatus.CREATED);
        return resposta;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO){
        LoginResponseDTO response = service.realizarLogin(loginDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/atualizar/usuarios/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable String id, @RequestBody @Valid UsuarioDTO usuarioDTO){
        usuarioDTO.setId(id);
        Usuario usuarioAtualizado = service.atualizarUsuario(usuarioDTO);
        ResponseEntity<Usuario> resposta = new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        return resposta;
    }

    @DeleteMapping("/excluir/usuarios/{id}")
    public void excluir(@PathVariable String id, @RequestBody UsuarioDTO usuarioDTO){
        usuarioDTO.setId(id);
        service.excluirUsuario(usuarioDTO);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<java.util.List<Usuario>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}