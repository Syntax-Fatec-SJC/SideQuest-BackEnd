package com.syntax.projetos_service.service.projetos.membroProjeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para remover membro do projeto
 */
@Service
public class RemoverMembroProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    /**
     * Remove membro do projeto
     */
    public ProjetoDTO executar(String projetoId, String usuarioId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));

        if (projeto.getUsuarioIds() == null || !projeto.getUsuarioIds().contains(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Usuário não é membro do projeto");
        }

        projeto.getUsuarioIds().remove(usuarioId);
        Projeto salvo = projetoRepositorio.save(projeto);
        return ConversorProjeto.converter(salvo);
    }
}
