package com.syntax.projetos_service.controller.projetos.listarProjetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.listarProjetos.ListarPorIdService;

/**
 * Controller para buscar projeto por ID Suporta ambas as rotas (antiga e nova)
 * para compatibilidade
 */
@RestController
public class ListarPorIdController {

    @Autowired
    private ListarPorIdService service;

    /**
     * Rota ANTIGA (mantida para compatibilidade) GET /listar/projetos/{id}
     */
    @GetMapping("/listar/projetos/{id}")
    public ResponseEntity<ProjetoDTO> buscarPorId(@PathVariable String id) {
        ProjetoDTO projeto = service.executar(id);
        return ResponseEntity.ok(projeto);
    }

    /**
     * Rota NOVA (usada pelo frontend atualizado) GET /projetos/{id}
     */
    @GetMapping("/projetos/{id}")
    public ResponseEntity<ProjetoDTO> buscarProjetoPorId(@PathVariable String id) {
        ProjetoDTO projeto = service.executar(id);
        return ResponseEntity.ok(projeto);
    }
}
