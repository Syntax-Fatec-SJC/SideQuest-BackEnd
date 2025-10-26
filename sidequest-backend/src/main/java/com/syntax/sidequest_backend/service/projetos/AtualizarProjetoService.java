package com.syntax.sidequest_backend.service.projetos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.conversor.ConversorProjeto;
import com.syntax.sidequest_backend.modelo.conversor.ConversorProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;

@Service
public class AtualizarProjetoService {

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	public ProjetoDTO executar(String id, ProjetoDTO dto) {
		Projeto existente = projetoRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

		Projeto atualizado = new ConversorProjetoDTO().paraEntidade(dto);
		atualizado.setId(id);

		List<String> usuarios = dto.getUsuarioIds() != null
				? new ArrayList<>(dto.getUsuarioIds())
				: copiarLista(existente.getUsuarioIds());

		usuarios = limparLista(usuarios);

		String criadorId = obterCriador(existente.getUsuarioIds());
		if (criadorId != null) {
			usuarios.remove(criadorId);
			usuarios.add(0, criadorId);
		}

		if (usuarios.isEmpty() && criadorId != null) {
			usuarios.add(criadorId);
		}

		atualizado.setUsuarioIds(usuarios);

		Projeto salvo = projetoRepositorio.save(atualizado);
		return ConversorProjeto.paraDTO(salvo);
	}

	private List<String> copiarLista(List<String> origem) {
		return origem == null ? new ArrayList<>() : new ArrayList<>(origem);
	}

	private List<String> limparLista(List<String> origem) {
		LinkedHashSet<String> conjunto = new LinkedHashSet<>();
		for (String id : origem) {
			if (id == null || id.isBlank()) {
				continue;
			}
			conjunto.add(id);
		}
		return new ArrayList<>(conjunto);
	}

	private String obterCriador(List<String> usuarioIds) {
		if (usuarioIds == null || usuarioIds.isEmpty()) {
			return null;
		}
		return usuarioIds.get(0);
	}
}
