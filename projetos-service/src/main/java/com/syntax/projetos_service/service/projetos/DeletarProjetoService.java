package com.syntax.projetos_service.service.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.client.AvisosClient;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para deletar projeto
 */
@Service
public class DeletarProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private AvisosClient avisosClient;

    public void executar(String id, String autorId, String autorNome) {
        Projeto projeto = projetoRepositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));
        
        // Cria avisos para TODOS os membros antes de deletar (incluindo o autor)
        if (autorNome != null && !autorNome.isBlank() && projeto.getUsuarioIds() != null) {
            for (String usuarioId : projeto.getUsuarioIds()) {
                avisosClient.criarAvisoProjetoExcluido(
                    projeto.getId(),
                    usuarioId,
                    autorId,
                    autorNome
                );
            }
        }
        
        projetoRepositorio.deleteById(id);
    }
}
