package com.syntax.sidequest_backend.controller.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.sidequest_backend.service.projetos.listarProjetos.ListarPorUsuarioService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("listarProjetosPorUsuarioController")
public class ListarPorUsuarioController {

	@Autowired
	private ListarPorUsuarioService listarPorUsuarioService;

	@GetMapping("/listar/{usuarioId}/projetos")
	public ResponseEntity<List<ProjetoDTO>> listarPorUsuario(@PathVariable String usuarioId) {
		return ResponseEntity.ok(listarPorUsuarioService.executar(usuarioId));
	}
}
