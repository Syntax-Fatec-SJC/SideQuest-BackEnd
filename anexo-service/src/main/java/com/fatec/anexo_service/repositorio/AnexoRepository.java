package com.fatec.anexo_service.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.anexo_service.entidade.Anexo;

@Repository
public interface AnexoRepository extends MongoRepository<Anexo, String> {

    // Buscar todos os anexos de uma tarefa
    List<Anexo> findByTarefaId(String tarefaId);

    // Deletar todos os anexos de uma tarefa
    void deleteByTarefaId(String tarefaId);

    // Contar anexos de uma tarefa
    long countByTarefaId(String tarefaId);
}
