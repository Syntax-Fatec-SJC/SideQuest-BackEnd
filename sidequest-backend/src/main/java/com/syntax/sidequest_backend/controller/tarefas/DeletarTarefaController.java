package com.syntax.sidequest_backend.controller.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.service.tarefas.DeletarTarefaService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
public class DeletarTarefaController {

	@Autowired
	private DeletarTarefaService deletarTarefaService;

	@DeleteMapping("/excluir/tarefas/{id}")
	public ResponseEntity<Void> excluir(@PathVariable String id) {
		deletarTarefaService.executar(id);
		return ResponseEntity.noContent().build();
	}
}
