package com.syntax.sidequest_backend.service.tarefas.listarTarefas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefa;
import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class ListarPorUsuarioService {

	@Autowired
	private TarefaRepositorio tarefaRepositorio;

	public List<TarefaDTO> executar(String usuarioId) {
		List<Tarefa> tarefas = tarefaRepositorio.findByUsuarioIdsContains(usuarioId);
		return ConversorTarefa.converter(tarefas);
	}
}
