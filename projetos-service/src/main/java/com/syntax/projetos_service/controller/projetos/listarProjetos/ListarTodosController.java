package com.syntax.projetos_service.controller.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.listarProjetos.ListarTodosService;

/**
 * Controller para listar todos os projetos Suporta ambas as rotas (antiga e
 * nova) para compatibilidade
 */
@RestController
public class ListarTodosController {

    @Autowired
    private ListarTodosService service;

    /**
     * Rota ANTIGA (mantida para compatibilidade) GET /listar/projetos
     */
    @GetMapping("/listar/projetos")
    public ResponseEntity<List<ProjetoDTO>> listarTodos() {
        List<ProjetoDTO> projetos = service.executar();
        return ResponseEntity.ok(projetos);
    }

    /**
     * Rota NOVA (usada pelo frontend atualizado) GET /projetos
     */
    @GetMapping("/projetos")
    public ResponseEntity<List<ProjetoDTO>> listarTodosProjetos() {
        List<ProjetoDTO> projetos = service.executar();
        return ResponseEntity.ok(projetos);
    }
}
