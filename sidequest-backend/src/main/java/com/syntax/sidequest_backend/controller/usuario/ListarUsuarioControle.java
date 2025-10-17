package com.syntax.sidequest_backend.controller.usuario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.conversor.ConversorUsuario;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.usuario.ListarUsuarioService;

@RestController
public class ListarUsuarioControle {

    @Autowired 
    private ListarUsuarioService servicoListarUsuario;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarTodos(){
        List<Usuario> usuarios = servicoListarUsuario.listarTodos();

        List<UsuarioDTO> usuarioDTO = usuarios.stream()
            .map(ConversorUsuario::converter)
            .collect(Collectors.toList());

        return ResponseEntity.ok(usuarioDTO);
    }
}
