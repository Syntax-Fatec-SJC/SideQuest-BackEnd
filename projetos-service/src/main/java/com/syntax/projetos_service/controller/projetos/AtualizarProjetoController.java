package com.syntax.projetos_service.controller.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.AtualizarProjetoService;

import jakarta.validation.Valid;

/**
 * Controller para atualizar projetos
 */
@RestController
public class AtualizarProjetoController {

    @Autowired
    private AtualizarProjetoService service;

    @PutMapping("/atualizar/projetos/{id}")
    public ResponseEntity<ProjetoDTO> atualizar(
            @PathVariable String id,
            @Valid @RequestBody ProjetoDTO dto) {
        
        ProjetoDTO resultado = service.executar(id, dto);
        return ResponseEntity.ok(resultado);
    }
}
