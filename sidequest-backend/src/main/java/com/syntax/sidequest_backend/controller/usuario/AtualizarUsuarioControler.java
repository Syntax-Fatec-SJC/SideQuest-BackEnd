package com.syntax.sidequest_backend.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.conversor.ConversorUsuario;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.AtualizarUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.usuario.AtualizarUsuarioService;
import com.syntax.sidequest_backend.service.usuario.BuscarUsuarioService;

import jakarta.validation.Valid;

@RestController
public class AtualizarUsuarioControler {

    @Autowired
    private AtualizarUsuarioService servicoAtualizarUsuario;

    @Autowired
    private BuscarUsuarioService servicoBuscarUsuario;

    @PutMapping("/atualizar/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable String id, @Valid @RequestBody AtualizarUsuarioDTO dto){
        Usuario usuario = servicoBuscarUsuario.buscarId(id);

        Usuario usuarioAtualizado = servicoAtualizarUsuario.atualizarUsuario(usuario, dto);

        UsuarioDTO resposta = ConversorUsuario.converter(usuarioAtualizado);

        return ResponseEntity.ok(resposta);
    }
}
