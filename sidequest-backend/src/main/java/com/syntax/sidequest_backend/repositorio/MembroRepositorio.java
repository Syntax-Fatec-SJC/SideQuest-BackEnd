package com.syntax.sidequest_backend.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.syntax.sidequest_backend.modelo.entidade.Membro;
import java.util.Optional;

@Repository
public interface MembroRepositorio extends MongoRepository<Membro, String> {
    Optional<Membro> findByEmail(String email);
}
