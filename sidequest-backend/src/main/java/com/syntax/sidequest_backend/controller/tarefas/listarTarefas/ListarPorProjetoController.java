package com.syntax.sidequest_backend.controller.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.service.tarefas.listarTarefas.ListarPorProjetoService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
public class ListarPorProjetoController {

	@Autowired
	private ListarPorProjetoService listarPorProjetoService;

	@GetMapping("/projetos/{projetoId}/tarefas")
	public ResponseEntity<List<TarefaDTO>> listarPorProjeto(@PathVariable String projetoId) {
		return ResponseEntity.ok(listarPorProjetoService.executar(projetoId));
	}
}
