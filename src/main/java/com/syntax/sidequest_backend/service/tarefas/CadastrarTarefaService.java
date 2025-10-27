package com.syntax.sidequest_backend.service.tarefas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefa;
import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefaDTO;
import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class CadastrarTarefaService {

	@Autowired
	private TarefaRepositorio tarefaRepositorio;

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	public TarefaDTO executar(TarefaDTO dto) {
		if (dto.getProjetoId() == null || dto.getProjetoId().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProjetoId é obrigatório");
		}

		Projeto projeto = projetoRepositorio.findById(dto.getProjetoId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

		List<String> usuarioIds = normalizarLista(dto.getUsuarioIds());
		validarUsuariosDoProjeto(usuarioIds, projeto);

		Tarefa tarefa = new ConversorTarefaDTO().converter(dto);
		tarefa.setUsuarioIds(usuarioIds);

		Tarefa salva = tarefaRepositorio.save(tarefa);
		return ConversorTarefa.converter(salva);
	}

	private List<String> normalizarLista(List<String> origem) {
		return origem == null ? new ArrayList<>() : new ArrayList<>(origem);
	}

	private void validarUsuariosDoProjeto(List<String> usuarioIds, Projeto projeto) {
		if (usuarioIds.isEmpty()) {
			return;
		}

		List<String> membros = projeto.getUsuarioIds();
		if (membros == null || membros.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projeto não possui membros cadastrados");
		}

		for (String usuarioId : usuarioIds) {
			if (!membros.contains(usuarioId)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Usuário " + usuarioId + " não está vinculado ao projeto informado");
			}
		}
	}
}
