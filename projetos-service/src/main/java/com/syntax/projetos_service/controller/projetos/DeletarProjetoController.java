package com.syntax.projetos_service.controller.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.service.projetos.DeletarProjetoService;

/**
 * Controller para deletar projetos
 */
@RestController
public class DeletarProjetoController {

    @Autowired
    private DeletarProjetoService service;

    @DeleteMapping("/excluir/projetos/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.executar(id);
        return ResponseEntity.noContent().build();
    }
}
