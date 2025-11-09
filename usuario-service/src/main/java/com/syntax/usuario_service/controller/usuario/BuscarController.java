package com.syntax.usuario_service.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.usuario_service.modelo.conversor.ConversorUsuario;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.service.usuario.BuscarUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class BuscarController {
    
    @Autowired
    private BuscarUsuarioService servicoBuscarUsuario;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable String id) {
        Usuario usuario = servicoBuscarUsuario.buscarPorId(id);
        UsuarioDTO usuarioDTO = ConversorUsuario.converter(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }
}
