package com.syntax.sidequest_backend.modelo.dto.tarefasDTO;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TarefaDTO {

    private String id;

    @NotNull
    private String nome;

    private Date prazoFinal;

    @NotNull
    private String status;

    private String comentario;

    private String descricao;

    private String projetoId;

    private List<String> anexos;

    private List<String> usuarioIds;
}
