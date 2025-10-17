package com.syntax.sidequest_backend.modelo.dto;

import java.util.Date;
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
    private String descricao;
    @Nullable
    private Date prazoFinal;
    @Nullable
    private List<String> usuarioIds;
}
