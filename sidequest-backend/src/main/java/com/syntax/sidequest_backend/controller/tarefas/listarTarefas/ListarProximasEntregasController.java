package com.syntax.sidequest_backend.controller.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.service.tarefas.listarTarefas.ListarProximasEntregasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class ListarProximasEntregasController {

	@Autowired
	private ListarProximasEntregasService service;

	@Operation(
		summary = "Listar próximas entregas do usuário",
		description = "Retorna as tarefas (incluindo atrasadas) com prazo definido atribuídas ao usuário, " +
		             "ordenadas por data de entrega mais próxima. Exclui apenas tarefas concluídas. Máximo de 10 tarefas."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de próximas entregas retornada com sucesso"),
		@ApiResponse(responseCode = "401", description = "Não autorizado"),
		@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
	})
	@GetMapping("/usuarios/{usuarioId}/proximas-entregas")
	public ResponseEntity<List<TarefaDTO>> listarProximasEntregas(
		@Parameter(description = "ID do usuário", required = true)
		@PathVariable String usuarioId
	) {
		List<TarefaDTO> tarefas = service.executar(usuarioId);
		return ResponseEntity.ok(tarefas);
	}
}
