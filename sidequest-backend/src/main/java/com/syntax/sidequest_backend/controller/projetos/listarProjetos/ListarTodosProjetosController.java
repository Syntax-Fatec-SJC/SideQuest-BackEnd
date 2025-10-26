package com.syntax.sidequest_backend.controller.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.sidequest_backend.service.projetos.listarProjetos.ListarTodosService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("listarTodosProjetosController")
public class ListarTodosProjetosController {

	@Autowired
	private ListarTodosService listarTodosService;

	@GetMapping("/listar/projetos")
	public ResponseEntity<List<ProjetoDTO>> listarTodos() {
		return ResponseEntity.ok(listarTodosService.executar());
	}
}
