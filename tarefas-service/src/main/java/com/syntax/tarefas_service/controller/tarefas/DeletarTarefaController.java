package com.syntax.tarefas_service.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.service.tarefas.DeletarTarefaService;

/**
 * Controller para deletar tarefas
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class DeletarTarefaController {

    @Autowired
    private DeletarTarefaService deletarTarefaService;

    @DeleteMapping("/excluir/tarefas/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        deletarTarefaService.executar(id);
        return ResponseEntity.noContent().build();
    }
}
