package com.syntax.sidequest_backend.service.tarefas.listarTarefas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.conversor.ConversorTarefa;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class ListarProximasEntregasService {

	@Autowired
	private TarefaRepositorio tarefaRepositorio;

	public List<TarefaDTO> executar(String usuarioId) {
		// Busca todas as tarefas do usuário
		List<Tarefa> tarefas = tarefaRepositorio.findByUsuarioIdsContains(usuarioId);

		List<Tarefa> proximasEntregas = tarefas.stream()
			.filter(t -> t.getPrazoFinal() != null)
			.filter(t -> !isStatusConcluido(t.getStatus()))
			.sorted((t1, t2) -> t1.getPrazoFinal().compareTo(t2.getPrazoFinal()))
			.limit(10)
			.collect(Collectors.toList());
		
		return ConversorTarefa.converter(proximasEntregas);
	}
	
	private boolean isStatusConcluido(String status) {
		if (status == null) {
			return false;
		}
		// Os estados possíveis são: PENDENTE, DESENVOLVIMENTO, CONCLUIDO
		return status.equalsIgnoreCase("CONCLUIDO") || 
		       status.equalsIgnoreCase("Concluído");
	}
}
