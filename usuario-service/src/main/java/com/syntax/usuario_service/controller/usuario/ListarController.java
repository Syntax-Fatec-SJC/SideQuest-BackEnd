package com.syntax.usuario_service.controller.usuario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.usuario_service.modelo.conversor.ConversorUsuario;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.service.usuario.ListarUsuarioService;

@RestController
@RequestMapping("/listar/usuarios")
public class ListarController {
    
    @Autowired
    private ListarUsuarioService servicoListarUsuario;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<Usuario> usuarios = servicoListarUsuario.listarTodos();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(ConversorUsuario::converter)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }
}
