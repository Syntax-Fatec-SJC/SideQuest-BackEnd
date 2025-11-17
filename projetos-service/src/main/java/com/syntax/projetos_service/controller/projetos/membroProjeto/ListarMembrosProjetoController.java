package com.syntax.projetos_service.controller.projetos.membroProjeto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.MembroDetalhadoDTO;
import com.syntax.projetos_service.service.projetos.membroProjeto.ListarMembrosProjetoService;

/**
 * CONTROLLER - LISTAR MEMBROS DO PROJETO
 * 
 * Endpoint: GET /projetos/{projetoId}/membros
 * Retorna a lista de membros (com detalhes completos) associados a um projeto específico.
 */
@RestController
@RequestMapping("/projetos")
public class ListarMembrosProjetoController {

    @Autowired
    private ListarMembrosProjetoService service;

    @GetMapping("/{projetoId}/membros")
    public ResponseEntity<List<MembroDetalhadoDTO>> listarMembros(@PathVariable String projetoId) {
        List<MembroDetalhadoDTO> membros = service.executar(projetoId);
        return ResponseEntity.ok(membros);
    }
}
