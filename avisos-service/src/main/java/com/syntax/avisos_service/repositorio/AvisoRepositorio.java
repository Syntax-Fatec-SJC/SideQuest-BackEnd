package com.syntax.avisos_service.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.syntax.avisos_service.modelo.entidade.Aviso;

/**
 * Repositório para operações de banco de dados com Avisos
 */
@Repository
public interface AvisoRepositorio extends MongoRepository<Aviso, String> {
    
    /**
     * Busca todos os avisos de um usuário
     */
    List<Aviso> findByUsuarioIdOrderByDataDesc(String usuarioId);
    
    /**
     * Busca avisos não visualizados de um usuário
     */
    List<Aviso> findByUsuarioIdAndVisualizadoOrderByDataDesc(String usuarioId, Boolean visualizado);
    
    /**
     * Busca avisos por tarefa
     */
    List<Aviso> findByTarefaId(String tarefaId);
    
    /**
     * Busca avisos por projeto
     */
    List<Aviso> findByProjetoId(String projetoId);
}
