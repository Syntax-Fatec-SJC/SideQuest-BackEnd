package com.syntax.tarefas_service.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.tarefas_service.modelo.entidade.Tarefa;

/**
 * Repositório para acesso aos dados de Tarefa no MongoDB
 */
@Repository
public interface TarefaRepositorio extends MongoRepository<Tarefa, String> {
    
    List<Tarefa> findByProjetoId(String projetoId);
    
    List<Tarefa> findByUsuarioIdsContaining(String usuarioId);
}
