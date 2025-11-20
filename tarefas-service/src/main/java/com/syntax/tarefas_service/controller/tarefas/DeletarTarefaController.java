package com.syntax.tarefas_service.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.service.tarefas.DeletarTarefaService;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
@RequestMapping("/tarefas")
public class DeletarTarefaController {

    @Autowired
    private DeletarTarefaService deletarTarefaService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable String id) {
        deletarTarefaService.executar(id);
        return ResponseEntity.ok(new java.util.HashMap<String, Object>() {
            {
                put("mensagem", "Tarefa deletada com sucesso");
                put("tarefaId", id);
                put("status", "deletado");
            }
        });
    }

    @DeleteMapping("/deletar-permanente/{id}")
    public ResponseEntity<?> deletarPermanente(@PathVariable String id) {
        deletarTarefaService.deletarPermanentemente(id);
        return ResponseEntity.ok(new java.util.HashMap<String, Object>() {
            {
                put("mensagem", "Tarefa deletada permanentemente");
                put("tarefaId", id);
            }
        });
    }
}
