package com.syntax.sidequest_backend.service.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefaDTO;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class ListarPorProjetoService {

	@Autowired
	private TarefaRepositorio tarefaRepositorio;

	@Autowired
	private ConversorTarefaDTO conversor;

	public List<TarefaDTO> executar(String projetoId) {
		List<Tarefa> tarefas = tarefaRepositorio.findByProjetoId(projetoId);
		return conversor.paraDTO(tarefas);
	}
}
