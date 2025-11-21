package com.syntax.calendario_service.repositorio;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.syntax.calendario_service.modelo.entidade.GoogleToken;

public interface GoogleTokenRepository extends MongoRepository<GoogleToken, String>{
    Optional<GoogleToken> findByUsuarioId(Long usuarioId);
}
