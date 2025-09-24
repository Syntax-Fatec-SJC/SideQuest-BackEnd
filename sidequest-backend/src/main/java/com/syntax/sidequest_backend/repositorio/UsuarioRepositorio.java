package com.syntax.sidequest_backend.repositorio;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de banco de dados da entidade Usuario
 * Extends MongoRepository para herdar métodos básicos de CRUD
 */
@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    /**
     * Busca usuário por email (único no sistema)
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca usuário por provedor e ID do provedor (para OAuth2)
     */
    Optional<Usuario> findByProvedorAndProvedorId(String provedor, String provedorId);

    /**
     * Verifica se existe usuário com o email especificado
     */
    boolean existsByEmail(String email);
}
