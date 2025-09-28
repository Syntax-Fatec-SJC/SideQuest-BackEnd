package com.syntax.sidequest_backend.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.sidequest_backend.modelo.entidade.Tarefa;

@Repository
public interface TarefaRepositorio extends MongoRepository<Tarefa, String> {
    List<Tarefa> findByProjetoId(String projetoId);

    List<Tarefa> findByUsuarioIdsContains(String usuarioId);
}
