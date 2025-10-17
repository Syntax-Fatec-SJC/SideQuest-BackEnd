package com.syntax.sidequest_backend.service.projetos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.conversor.ConversorProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;

@Service
public class CadastrarProjetoService {

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	@Autowired
	private ConversorProjetoDTO conversor;

	public ProjetoDTO executar(ProjetoDTO dto, String usuarioIdCriador) {
		if (usuarioIdCriador == null || usuarioIdCriador.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioIdCriador é obrigatório");
		}

		Projeto projeto = conversor.paraEntidade(dto);
		List<String> usuarios = montarListaDeUsuarios(dto.getUsuarioIds(), usuarioIdCriador);
		projeto.setUsuarioIds(usuarios);

		Projeto salvo = projetoRepositorio.save(projeto);
		return conversor.paraDTO(salvo);
	}

	private List<String> montarListaDeUsuarios(List<String> origem, String usuarioIdCriador) {
		List<String> usuarios = new ArrayList<>();
		usuarios.add(usuarioIdCriador);

		if (origem == null) {
			return usuarios;
		}

		for (String id : origem) {
			if (id == null || id.isBlank() || id.equals(usuarioIdCriador) || usuarios.contains(id)) {
				continue;
			}
			usuarios.add(id);
		}
		return usuarios;
	}
}
