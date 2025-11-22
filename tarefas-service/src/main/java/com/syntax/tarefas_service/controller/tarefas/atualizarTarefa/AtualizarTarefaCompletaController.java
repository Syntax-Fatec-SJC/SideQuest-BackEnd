package com.syntax.tarefas_service.controller.tarefas.atualizarTarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.atualizarTarefa.AtualizarTarefaCompletaService;

import jakarta.validation.Valid;

/**
 * Controller para atualização completa de tarefa
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class AtualizarTarefaCompletaController {

    @Autowired
    private AtualizarTarefaCompletaService service;

    @PutMapping("/atualizar/tarefas/{id}")
    public ResponseEntity<TarefaDTO> atualizar(
            @PathVariable String id, 
            @Valid @RequestBody TarefaDTO dto,
            jakarta.servlet.http.HttpServletRequest request) {
        
        String autorId = (String) request.getAttribute("userId");
        String autorNome = (String) request.getAttribute("userName");
        
        TarefaDTO atualizada = service.executar(id, dto, autorId, autorNome);
        return ResponseEntity.ok(atualizada);
    }
}
