package com.syntax.sidequest_backend.modelo.dto;

import java.util.List;

import com.mongodb.lang.Nullable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjetoDTO {
    private String id;
    @NotNull
    private String status;
    @NotNull
    private String nome;
    @Nullable
    private List<String> usuarioIds;
}
