package com.fatec.anexo_service.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.anexo_service.entidade.Anexo;

@Repository
public interface AnexoRepository extends MongoRepository<Anexo, String> {

    List<Anexo> findByTarefaId(String tarefaId);

    void deleteByTarefaId(String tarefaId);

    long countByTarefaId(String tarefaId);
}
