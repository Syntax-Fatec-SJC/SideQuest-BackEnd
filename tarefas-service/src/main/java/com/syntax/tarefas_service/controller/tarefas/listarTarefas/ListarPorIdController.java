package com.syntax.tarefas_service.controller.tarefas.listarTarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.listarTarefas.ListarPorIdService;

/**
 * Controller para buscar tarefa por ID
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class ListarPorIdController {

    @Autowired
    private ListarPorIdService listarPorIdService;

    @GetMapping("/tarefas/{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(listarPorIdService.executar(id));
    }
}
