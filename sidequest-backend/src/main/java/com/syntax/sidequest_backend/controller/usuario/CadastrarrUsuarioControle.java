package com.syntax.sidequest_backend.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.conversor.ConversorUsuarioDTO;
import com.syntax.sidequest_backend.modelo.dto.usuarioDTO.CadastrarUsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.usuario.CadastrarUsuarioService;

import jakarta.validation.Valid;

@RestController
public class CadastrarrUsuarioControle {
    @Autowired 
    private CadastrarUsuarioService servicoCadastrarUsuario;

    @PostMapping("/cadastrar/usuarios")
    public ResponseEntity<Usuario> cadastro(@Valid @RequestBody CadastrarUsuarioDTO dto){
        Usuario usuario = ConversorUsuarioDTO.converter(dto);
        servicoCadastrarUsuario.cadastraUsuario(usuario);
        ResponseEntity<Usuario> resposta = new ResponseEntity<>(usuario, HttpStatus.CREATED);

        return resposta;
    }
}
