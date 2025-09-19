package com.syntax.sidequest_backend.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.sidequest_backend.modelo.entidade.Projeto;

@Repository
public interface ProjetoRepositorio extends MongoRepository<Projeto, String> {
}
