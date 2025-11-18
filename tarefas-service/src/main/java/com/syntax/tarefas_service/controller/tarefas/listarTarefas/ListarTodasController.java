package com.syntax.tarefas_service.controller.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.listarTarefas.ListarTodasService;

/**
 * Controller para listar todas as tarefas
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class ListarTodasController {

    @Autowired
    private ListarTodasService listarTodasService;

    @GetMapping("/listar/tarefas")
    public ResponseEntity<List<TarefaDTO>> listarTodas() {
        return ResponseEntity.ok(listarTodasService.executar());
    }
}
