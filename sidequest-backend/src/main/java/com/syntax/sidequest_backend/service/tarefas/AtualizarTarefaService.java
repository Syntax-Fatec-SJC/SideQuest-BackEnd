package com.syntax.sidequest_backend.service.tarefas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefa;
import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefaDTO;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class AtualizarTarefaService {

	@Autowired
	private TarefaRepositorio tarefaRepositorio;

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	public TarefaDTO executar(String id, TarefaDTO dto) {
		Tarefa existente = tarefaRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

		String projetoId = dto.getProjetoId();
		if (projetoId == null || projetoId.isBlank()) {
			projetoId = existente.getProjetoId();
		}

		Projeto projeto = projetoRepositorio.findById(projetoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto associado não encontrado"));

		List<String> usuarioIds = normalizarLista(dto.getUsuarioIds());
		validarUsuariosDoProjeto(usuarioIds, projeto);

		Tarefa atualizado = new ConversorTarefaDTO().converter(dto);
		atualizado.setId(id);
		atualizado.setProjetoId(projetoId);
		atualizado.setUsuarioIds(usuarioIds);

		Tarefa salvo = tarefaRepositorio.save(atualizado);
		return ConversorTarefa.converter(salvo);
	}

	public TarefaDTO atualizarResponsaveis(String tarefaId, List<String> usuarioIds) {
		Tarefa tarefa = tarefaRepositorio.findById(tarefaId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

		List<String> normalizados = normalizarLista(usuarioIds);
		Projeto projeto = projetoRepositorio.findById(tarefa.getProjetoId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto associado não encontrado"));

		validarUsuariosDoProjeto(normalizados, projeto);

		tarefa.setUsuarioIds(normalizados);
		Tarefa salvo = tarefaRepositorio.save(tarefa);
		return ConversorTarefa.converter(salvo);
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

		Set<String> membrosSet = new HashSet<>(membros);
		for (String usuarioId : usuarioIds) {
			if (!membrosSet.contains(usuarioId)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Usuário " + usuarioId + " não está vinculado ao projeto informado");
			}
		}
	}
}
