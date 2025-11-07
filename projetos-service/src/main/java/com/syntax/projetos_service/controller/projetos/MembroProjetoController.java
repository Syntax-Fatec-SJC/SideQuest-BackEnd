package com.syntax.projetos_service.controller.projetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.MembroProjetoDTO;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.MembroProjetoService;

import jakarta.validation.Valid;

/**
 * Controller para gerenciar membros de projetos
 */
@RestController
public class MembroProjetoController {

    @Autowired
    private MembroProjetoService service;

    @PostMapping("/projetos/{projetoId}/membros")
    public ResponseEntity<ProjetoDTO> adicionarMembro(
            @PathVariable String projetoId,
            @Valid @RequestBody MembroProjetoDTO dto) {
        
        ProjetoDTO resultado = service.adicionarMembro(projetoId, dto);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/projetos/{projetoId}/membros/{usuarioId}")
    public ResponseEntity<ProjetoDTO> removerMembro(
            @PathVariable String projetoId,
            @PathVariable String usuarioId) {
        
        ProjetoDTO resultado = service.removerMembro(projetoId, usuarioId);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/projetos/{projetoId}/membros")
    public ResponseEntity<List<String>> listarMembros(@PathVariable String projetoId) {
        List<String> membros = service.listarMembros(projetoId);
        return ResponseEntity.ok(membros);
    }
}
