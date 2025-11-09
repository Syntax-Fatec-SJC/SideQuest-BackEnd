package com.syntax.projetos_service.service.projetos.membroProjeto;

import java.util.ArrayList;

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
 * Service para adicionar membros ao projeto
 */
@Service
public class AdicionarMembroProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    /**
     * Adiciona membro(s) ao projeto
     */
    public ProjetoDTO executar(String projetoId, MembroProjetoDTO dto) {
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
}
