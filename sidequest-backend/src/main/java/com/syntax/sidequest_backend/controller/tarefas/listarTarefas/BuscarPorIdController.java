package com.syntax.sidequest_backend.controller.tarefas.listarTarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.service.tarefas.listarTarefas.BuscarPorIdService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
public class BuscarPorIdController {

	@Autowired
	private BuscarPorIdService buscarPorIdService;

	@GetMapping("/buscar/tarefas/{id}")
	public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable String id) {
		return ResponseEntity.ok(buscarPorIdService.executar(id));
	}
}
