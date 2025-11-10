package com.syntax.tarefas_service.controller.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.listarTarefas.ListarPorUsuarioService;

/**
 * Controller para listar tarefas por usuário
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class ListarPorUsuarioController {

    @Autowired
    private ListarPorUsuarioService listarPorUsuarioService;

    @GetMapping("/tarefas/minhas")
    public ResponseEntity<List<TarefaDTO>> listarPorUsuario(@RequestHeader("X-User-Id") String usuarioId) {
        return ResponseEntity.ok(listarPorUsuarioService.executar(usuarioId));
    }
}
