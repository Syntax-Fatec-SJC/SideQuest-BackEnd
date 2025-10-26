package com.syntax.sidequest_backend.controller.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.service.tarefas.listarTarefas.ListarPorUsuarioService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
public class ListarPorUsuarioController {

	@Autowired
	private ListarPorUsuarioService listarPorUsuarioService;

	@GetMapping("/usuarios/{usuarioId}/tarefas")
	public ResponseEntity<List<TarefaDTO>> listarPorUsuario(@PathVariable String usuarioId) {
		return ResponseEntity.ok(listarPorUsuarioService.executar(usuarioId));
	}
}
