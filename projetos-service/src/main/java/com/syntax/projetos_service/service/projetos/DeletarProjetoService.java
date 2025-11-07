package com.syntax.projetos_service.service.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para deletar projeto
 */
@Service
public class DeletarProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    public void executar(String id) {
        if (!projetoRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado");
        }
        
        projetoRepositorio.deleteById(id);
    }
}
