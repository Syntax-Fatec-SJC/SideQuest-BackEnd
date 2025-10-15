package com.syntax.sidequest_backend.controller.projetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.MembroProjetoDTO;
import com.syntax.sidequest_backend.service.projetos.MembroProjetoService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("membroProjetoController")
public class MembroProjetoController {

	@Autowired
	private MembroProjetoService membroProjetoService;

	@GetMapping("/listar/{projetoId}/membros")
	public ResponseEntity<List<MembroProjetoDTO>> listarMembros(@PathVariable String projetoId) {
		List<MembroProjetoDTO> membros = membroProjetoService.listarMembros(projetoId);
		return ResponseEntity.ok(membros);
	}

	@PostMapping("/adicionar/{projetoId}/membros/{usuarioId}")
	public ResponseEntity<Void> adicionarMembro(@PathVariable String projetoId, @PathVariable String usuarioId) {
		membroProjetoService.adicionarMembro(projetoId, usuarioId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/excluir/{projetoId}/membros/{usuarioId}")
	public ResponseEntity<Void> removerMembro(@PathVariable String projetoId, @PathVariable String usuarioId) {
		membroProjetoService.removerMembro(projetoId, usuarioId);
		return ResponseEntity.noContent().build();
	}
}
