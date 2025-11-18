package com.syntax.projetos_service.service.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para listar todos os projetos
 */
@Service
public class ListarTodosService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    public List<ProjetoDTO> executar() {
        return projetoRepositorio.findAll().stream()
            .map(ConversorProjeto::converter)
            .toList();
    }
}
