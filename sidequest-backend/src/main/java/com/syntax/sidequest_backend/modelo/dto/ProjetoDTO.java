package com.syntax.sidequest_backend.modelo.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProjetoDTO {
    private String id;
    @NotNull
    private String status;
    @NotNull
    private String nome;
    @NotEmpty
    private List<String> usuariosIds;
    private List<String> tarefasIds;
}
