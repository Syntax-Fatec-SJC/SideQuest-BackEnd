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
    public ResponseEntity<Void> deletar(
            @PathVariable String id,
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-User-Id", required = false) String autorId,
            @org.springframework.web.bind.annotation.RequestHeader(value = "X-User-Name", required = false) String autorNome) {
        service.executar(id, autorId, autorNome);
        return ResponseEntity.noContent().build();
    }
}
