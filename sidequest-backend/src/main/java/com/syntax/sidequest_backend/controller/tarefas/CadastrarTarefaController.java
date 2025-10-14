package com.syntax.sidequest_backend.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.service.tarefas.CadastrarTarefaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
public class CadastrarTarefaController {

	@Autowired
	private CadastrarTarefaService cadastrarTarefaService;

	@PostMapping("/cadastrar/tarefas")
	public ResponseEntity<TarefaDTO> cadastrar(@Valid @RequestBody TarefaDTO dto) {
		TarefaDTO criada = cadastrarTarefaService.executar(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(criada);
	}
}
