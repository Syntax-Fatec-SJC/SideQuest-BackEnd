package com.syntax.tarefas_service.controller.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.listarTarefas.ListarProximasEntregasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller para listar as próximas entregas de tarefas do usuário autenticado
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
@RequestMapping("/listar/tarefas")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class ListarProximasEntregasController {

    @Autowired
    private ListarProximasEntregasService service;

    @Operation(
        summary = "Listar minhas próximas entregas",
        description = "Retorna as tarefas (incluindo atrasadas) com prazo definido atribuídas ao usuário autenticado, " +
                     "ordenadas por data de entrega mais próxima. Exclui apenas tarefas concluídas. Máximo de 10 tarefas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de próximas entregas retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/proximas-entregas")
    public ResponseEntity<List<TarefaDTO>> listarProximasEntregas(
        @RequestAttribute("userId") String usuarioId
    ) {
        if (usuarioId == null || usuarioId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
        }

        List<TarefaDTO> tarefas = service.executar(usuarioId);
        return ResponseEntity.ok(tarefas);
    }
}
