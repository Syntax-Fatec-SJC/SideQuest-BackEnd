package com.syntax.sidequest_backend.modelo.conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;

@Service
public class ConversorProjetoDTO {

	public Projeto paraEntidade(ProjetoDTO dto) {
		if (dto == null) {
			return null;
		}

		Projeto projeto = new Projeto();
		projeto.setId(dto.getId());
		projeto.setNome(dto.getNome());
		projeto.setStatus(dto.getStatus());
		projeto.setDescricao(dto.getDescricao());
		projeto.setPrazoFinal(dto.getPrazoFinal());
		projeto.setUsuarioIds(copiarLista(dto.getUsuarioIds()));
		return projeto;
	}

	public ProjetoDTO paraDTO(Projeto projeto) {
		if (projeto == null) {
			return null;
		}

		ProjetoDTO dto = new ProjetoDTO();
		dto.setId(projeto.getId());
		dto.setNome(projeto.getNome());
		dto.setStatus(projeto.getStatus());
		dto.setDescricao(projeto.getDescricao());
		dto.setPrazoFinal(projeto.getPrazoFinal());
		dto.setUsuarioIds(copiarLista(projeto.getUsuarioIds()));
		return dto;
	}

	public List<ProjetoDTO> paraDTO(List<Projeto> projetos) {
		if (projetos == null || projetos.isEmpty()) {
			return new ArrayList<>();
		}

		return projetos.stream()
				.filter(Objects::nonNull)
				.map(this::paraDTO)
				.collect(Collectors.toList());
	}

	private List<String> copiarLista(List<String> origem) {
		if (origem == null || origem.isEmpty()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(origem);
	}
}
