package com.syntax.sidequest_backend.controller.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.service.projetos.CadastrarProjetoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("cadastrarProjetoController")
public class CadastrarProjetoController {

	@Autowired
	private CadastrarProjetoService cadastrarProjetoService;

	@PostMapping("/cadastrar/projetos")
	public ResponseEntity<ProjetoDTO> cadastrar(@RequestParam("usuarioIdCriador") String usuarioIdCriador,
			@Valid @RequestBody ProjetoDTO dto) {
		ProjetoDTO criado = cadastrarProjetoService.executar(dto, usuarioIdCriador);
		return ResponseEntity.status(HttpStatus.CREATED).body(criado);
	}
}
