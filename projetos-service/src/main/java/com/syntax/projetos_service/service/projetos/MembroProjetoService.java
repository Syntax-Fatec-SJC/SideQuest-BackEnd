package com.syntax.projetos_service.service.projetos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.MembroProjetoDTO;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para gerenciar membros do projeto
 */
@Service
public class MembroProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    /**
     * Adiciona membro ao projeto
     */
    public ProjetoDTO adicionarMembro(String projetoId, MembroProjetoDTO dto) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));

        if (projeto.getUsuarioIds() == null) {
            projeto.setUsuarioIds(new ArrayList<>());
        }

        // Adicionar múltiplos usuários se fornecidos
        if (dto.getUsuarioIds() != null) {
            for (String usuarioId : dto.getUsuarioIds()) {
                if (!projeto.getUsuarioIds().contains(usuarioId)) {
                    projeto.getUsuarioIds().add(usuarioId);
                }
            }
        }

        Projeto salvo = projetoRepositorio.save(projeto);
        return ConversorProjeto.converter(salvo);
    }

    /**
     * Remove membro do projeto
     */
    public ProjetoDTO removerMembro(String projetoId, String usuarioId) {
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

    /**
     * Lista membros do projeto
     */
    public List<String> listarMembros(String projetoId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));

        return projeto.getUsuarioIds() != null ? projeto.getUsuarioIds() : new ArrayList<>();
    }
}
