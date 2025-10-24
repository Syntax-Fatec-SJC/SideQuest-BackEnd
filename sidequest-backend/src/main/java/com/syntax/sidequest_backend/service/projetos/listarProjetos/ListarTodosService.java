package com.syntax.sidequest_backend.service.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.conversor.ConversorProjeto;
import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;

@Service("listarTodosProjetosService")
public class ListarTodosService {

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	public List<ProjetoDTO> executar() {
		List<Projeto> projetos = projetoRepositorio.findAll();
		return ConversorProjeto.paraDTO(projetos);
	}
}
