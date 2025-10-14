package com.syntax.sidequest_backend.modelo.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;

@Service
public class ConversorTarefaDTO {

	public Tarefa paraEntidade(TarefaDTO dto) {
		if (dto == null) {
			return null;
		}

		Tarefa tarefa = new Tarefa();
		tarefa.setId(dto.getId());
		tarefa.setNome(dto.getNome());
		tarefa.setPrazoFinal(dto.getPrazoFinal());
		tarefa.setStatus(dto.getStatus());
		tarefa.setComentario(dto.getComentario());
		tarefa.setDescricao(dto.getDescricao());
		tarefa.setProjetoId(dto.getProjetoId());
		tarefa.setAnexos(copiarLista(dto.getAnexos()));
		tarefa.setUsuarioIds(copiarLista(dto.getUsuarioIds()));
		return tarefa;
	}

	public TarefaDTO paraDTO(Tarefa tarefa) {
		if (tarefa == null) {
			return null;
		}

		TarefaDTO dto = new TarefaDTO();
		dto.setId(tarefa.getId());
		dto.setNome(tarefa.getNome());
		dto.setPrazoFinal(tarefa.getPrazoFinal());
		dto.setStatus(tarefa.getStatus());
		dto.setComentario(tarefa.getComentario());
		dto.setDescricao(tarefa.getDescricao());
		dto.setProjetoId(tarefa.getProjetoId());
		dto.setAnexos(copiarLista(tarefa.getAnexos()));
		dto.setUsuarioIds(copiarLista(tarefa.getUsuarioIds()));
		return dto;
	}

	private List<String> copiarLista(List<String> origem) {
		if (origem == null || origem.isEmpty()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(origem);
	}
}
