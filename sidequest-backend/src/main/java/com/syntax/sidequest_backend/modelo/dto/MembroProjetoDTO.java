package com.syntax.sidequest_backend.modelo.dto;

public record MembroProjetoDTO(
    String usuarioId,
    String nome,
    String email,
    boolean criador
) {}
