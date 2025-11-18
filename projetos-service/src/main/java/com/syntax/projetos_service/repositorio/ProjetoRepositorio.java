package com.syntax.projetos_service.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.projetos_service.modelo.entidade.Projeto;

/**
 * Repositório para acesso aos dados de Projeto no MongoDB
 */
@Repository
public interface ProjetoRepositorio extends MongoRepository<Projeto, String> {
    
    List<Projeto> findByUsuarioIdsContaining(String usuarioId);
}
