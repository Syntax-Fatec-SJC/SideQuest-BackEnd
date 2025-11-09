package com.syntax.projetos_service.service.projetos.membroProjeto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para listar membros do projeto
 */
@Service
public class ListarMembrosProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    /**
     * Lista todos os membros do projeto
     */
    public List<String> executar(String projetoId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));

        return projeto.getUsuarioIds() != null ? projeto.getUsuarioIds() : new ArrayList<>();
    }
}
