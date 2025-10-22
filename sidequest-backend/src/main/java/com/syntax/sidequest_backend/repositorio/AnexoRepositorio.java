package com.syntax.sidequest_backend.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.sidequest_backend.modelo.entidade.Anexo;

@Repository
public interface AnexoRepositorio extends MongoRepository<Anexo, String> {

    List<Anexo> findByTarefaId(String tarefaId);

    void deleteByTarefaId(String tarefaId);
}
