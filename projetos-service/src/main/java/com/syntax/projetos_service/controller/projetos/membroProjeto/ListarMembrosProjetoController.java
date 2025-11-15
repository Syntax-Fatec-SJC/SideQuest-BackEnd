package com.syntax.projetos_service.controller.projetos.membroProjeto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.service.projetos.membroProjeto.ListarMembrosProjetoService;

/**
 * Controller para listar membros do projeto
 */
@RestController
public class ListarMembrosProjetoController {

    @Autowired
    private ListarMembrosProjetoService service;

    @GetMapping("/projetos/{projetoId}/membros")
    public ResponseEntity<List<String>> listarMembros(@PathVariable String projetoId) {
        List<String> membros = service.executar(projetoId);
        return ResponseEntity.ok(membros);
    }
}
