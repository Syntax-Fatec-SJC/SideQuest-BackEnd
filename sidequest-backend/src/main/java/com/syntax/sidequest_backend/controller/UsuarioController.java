package com.syntax.sidequest_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.interfaces.IUsuarioService;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {
    
    private final IUsuarioService service;
    
    public UsuarioController(IUsuarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar/usuarios")
    public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuarioCriado = service.criar(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO){
        LoginResponseDTO response = service.realizarLogin(loginDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar/usuarios/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable String id, @RequestBody @Valid UsuarioDTO usuarioDTO){
        usuarioDTO.setId(id);
        Usuario usuarioAtualizado = service.atualizar(usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/excluir/usuarios/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<java.util.List<Usuario>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
