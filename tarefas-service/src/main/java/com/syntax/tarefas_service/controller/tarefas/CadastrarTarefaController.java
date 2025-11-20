package com.syntax.tarefas_service.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.CadastrarTarefaService;

import jakarta.validation.Valid;

/**
 * Controller para cadastrar nova tarefa
 */
@RestController
public class CadastrarTarefaController {

    @Autowired
    private CadastrarTarefaService cadastrarTarefaService;

    @PostMapping("/cadastrar/tarefas")
    public ResponseEntity<TarefaDTO> cadastrar(@Valid @RequestBody TarefaDTO dto) {
        TarefaDTO criada = cadastrarTarefaService.executar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }
}
