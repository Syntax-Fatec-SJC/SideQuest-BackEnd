package com.syntax.projetos_service.service.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para deletar projeto (com lixeira)
 */
@Service
public class DeletarProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    /**
     * Envia o projeto para a lixeira (soft delete)
     */
    public void executar(String id) {
        Projeto projeto = projetoRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        // Marca como excluído (lixeira)
        projeto.setExcluido(true);
        projetoRepositorio.save(projeto);
    }

    /**
     * Deleta permanentemente do banco (hard delete)
     */
    public void deletarPermanentemente(String id) {
        if (!projetoRepositorio.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Projeto não encontrado");
        }

        projetoRepositorio.deleteById(id);
    }
}
