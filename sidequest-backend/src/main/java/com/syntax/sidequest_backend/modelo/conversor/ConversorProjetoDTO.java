package com.syntax.sidequest_backend.modelo.conversor;

import java.util.ArrayList;
import java.util.List;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;

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

	private List<String> copiarLista(List<String> origem) {
		if (origem == null || origem.isEmpty()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(origem);
	}
}
