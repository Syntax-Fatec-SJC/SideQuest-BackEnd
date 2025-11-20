package com.syntax.tarefas_service.controller.tarefas.atualizarTarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RestController
public class AtualizarTarefaCompletaController {

    @Autowired
    private AtualizarTarefaCompletaService service;

    @PutMapping("/atualizar/tarefas/{id}")
    public ResponseEntity<TarefaDTO> atualizar(@PathVariable String id, @Valid @RequestBody TarefaDTO dto) {
        TarefaDTO atualizada = service.executar(id, dto);
        return ResponseEntity.ok(atualizada);
    }
}
