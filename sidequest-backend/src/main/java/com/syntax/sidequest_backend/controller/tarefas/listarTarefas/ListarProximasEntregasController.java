package com.syntax.sidequest_backend.controller.tarefas.listarTarefas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;
import com.syntax.sidequest_backend.service.tarefas.listarTarefas.ListarProximasEntregasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class ListarProximasEntregasController {

    @Autowired
    private ListarProximasEntregasService service;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Operation(
        summary = "Listar minhas próximas entregas",
        description = "Retorna as tarefas (incluindo atrasadas) com prazo definido atribuídas ao usuário autenticado, " +
                     "ordenadas por data de entrega mais próxima. Exclui apenas tarefas concluídas. Máximo de 10 tarefas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de próximas entregas retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/tarefas/proximas-entregas")
    public ResponseEntity<List<TarefaDTO>> listarProximasEntregas(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        String usuarioId = usuarioOpt.get().getId().toString();

        List<TarefaDTO> tarefas = service.executar(usuarioId);
        return ResponseEntity.ok(tarefas);
    }
}
