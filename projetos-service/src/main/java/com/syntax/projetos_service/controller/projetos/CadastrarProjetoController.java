package com.syntax.projetos_service.controller.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.CadastrarProjetoService;

import jakarta.validation.Valid;

/**
 * Controller para cadastrar projetos
 */
@RestController
public class CadastrarProjetoController {

    @Autowired
    private CadastrarProjetoService service;

    @PostMapping("/cadastrar/projetos")
    public ResponseEntity<ProjetoDTO> cadastrar(
            @Valid @RequestBody ProjetoDTO dto,
            @RequestHeader("usuarioId") String usuarioId) {
        
        ProjetoDTO resultado = service.executar(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
