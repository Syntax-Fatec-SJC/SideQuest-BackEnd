package com.syntax.projetos_service.service.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.client.AvisosClient;
import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para atualizar projeto existente
 */
@Service
public class AtualizarProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private AvisosClient avisosClient;

    public ProjetoDTO executar(String id, ProjetoDTO dto, String autorId, String autorNome) {
        Projeto projetoExistente = projetoRepositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));

        // Atualizar campos
        if (dto.getNome() != null) {
            projetoExistente.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            projetoExistente.setDescricao(dto.getDescricao());
        }
        if (dto.getPrazoFinal() != null) {
            projetoExistente.setPrazoFinal(dto.getPrazoFinal());
        }
        if (dto.getStatus() != null) {
            projetoExistente.setStatus(dto.getStatus());
        }

        Projeto atualizado = projetoRepositorio.save(projetoExistente);
        
        // Cria avisos para TODOS os membros do projeto (incluindo o autor)
        if (autorNome != null && !autorNome.isBlank() && atualizado.getUsuarioIds() != null) {
            for (String usuarioId : atualizado.getUsuarioIds()) {
                avisosClient.criarAvisoProjetoAtualizado(
                    atualizado.getId(),
                    usuarioId,
                    autorId,
                    autorNome
                );
            }
        }
        
        return ConversorProjeto.converter(atualizado);
    }
}
