package com.syntax.sidequest_backend.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.dto.tarefas.AtualizarResponsaveisDTO;
import com.syntax.sidequest_backend.service.tarefas.AtualizarTarefaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
public class AtualizarTarefaController {

	@Autowired
	private AtualizarTarefaService atualizarTarefaService;

	@PutMapping("/atualizar/tarefas/{id}")
	public ResponseEntity<TarefaDTO> atualizar(@PathVariable String id, @Valid @RequestBody TarefaDTO dto) {
		TarefaDTO atualizada = atualizarTarefaService.executar(id, dto);
		return ResponseEntity.ok(atualizada);
	}

	@PatchMapping("/tarefas/{id}/responsaveis")
	public ResponseEntity<TarefaDTO> atualizarResponsaveis(@PathVariable String id,
						@RequestBody AtualizarResponsaveisDTO dto) {
		TarefaDTO atualizada = atualizarTarefaService.atualizarResponsaveis(id,
				dto == null ? null : dto.getUsuarioIds());
		return ResponseEntity.ok(atualizada);
	}
}
