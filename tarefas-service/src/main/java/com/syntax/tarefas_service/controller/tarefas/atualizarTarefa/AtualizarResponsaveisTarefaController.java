package com.syntax.tarefas_service.controller.tarefas.atualizarTarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.AtualizarResponsaveisDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.atualizarTarefa.AtualizarResponsaveisTarefaService;

/**
 * Controller para atualizar responsáveis da tarefa
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class AtualizarResponsaveisTarefaController {

    @Autowired
    private AtualizarResponsaveisTarefaService service;

    @PatchMapping("/tarefas/{id}/responsaveis")
    public ResponseEntity<TarefaDTO> atualizarResponsaveis(@PathVariable String id,
                        @RequestBody AtualizarResponsaveisDTO dto) {
        TarefaDTO atualizada = service.executar(id, dto == null ? null : dto.getUsuarioIds());
        return ResponseEntity.ok(atualizada);
    }
}
