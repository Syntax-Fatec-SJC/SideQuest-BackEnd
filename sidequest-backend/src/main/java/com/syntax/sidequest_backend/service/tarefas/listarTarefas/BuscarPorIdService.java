package com.syntax.sidequest_backend.service.tarefas.listarTarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefaDTO;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class BuscarPorIdService {

	@Autowired
	private TarefaRepositorio tarefaRepositorio;

	@Autowired
	private ConversorTarefaDTO conversor;

	public TarefaDTO executar(String id) {
		Tarefa tarefa = tarefaRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
		return conversor.paraDTO(tarefa);
	}
}
