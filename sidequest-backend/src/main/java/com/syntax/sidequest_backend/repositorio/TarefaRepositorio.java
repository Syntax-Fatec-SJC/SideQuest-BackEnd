package com.syntax.sidequest_backend.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.sidequest_backend.modelo.entidade.Tarefa;

@Repository
public interface TarefaRepositorio extends MongoRepository<Tarefa, String> {
}
