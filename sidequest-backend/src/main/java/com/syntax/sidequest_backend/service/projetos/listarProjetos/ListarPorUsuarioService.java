package com.syntax.sidequest_backend.service.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.conversor.ConversorProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;

@Service("listarProjetosPorUsuarioService")
public class ListarPorUsuarioService {

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	@Autowired
	private ConversorProjetoDTO conversor;

	public List<ProjetoDTO> executar(String usuarioId) {
		List<Projeto> projetos = projetoRepositorio.findByUsuarioIdsContaining(usuarioId);
		return conversor.paraDTO(projetos);
	}
}
