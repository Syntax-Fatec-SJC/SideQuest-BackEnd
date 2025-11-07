package com.syntax.projetos_service.service.projetos.listarProjetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para buscar projeto por ID
 */
@Service
public class ListarPorIdService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    public ProjetoDTO executar(String id) {
        Projeto projeto = projetoRepositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));
        
        return ConversorProjeto.converter(projeto);
    }
}
