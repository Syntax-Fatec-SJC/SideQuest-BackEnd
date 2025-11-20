package com.syntax.tarefas_service.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.RestaurarTarefaService;

@RestController
@RequestMapping("/tarefas/lixeira")
public class RestaurarTarefaController {

    @Autowired
    private RestaurarTarefaService restaurarService;

    @PostMapping("/restaurar")
    public ResponseEntity<TarefaDTO> restaurar(@RequestBody TarefaDTO dto) {
        // Validação mínima
        if (dto.getId() == null || dto.getId().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        TarefaDTO restaurada = restaurarService.executar(dto);
        return ResponseEntity.ok(restaurada);
    }
}
