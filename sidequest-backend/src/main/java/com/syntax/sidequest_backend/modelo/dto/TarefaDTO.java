package com.syntax.sidequest_backend.modelo.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TarefaDTO {

    private String id;

    @NotBlank(message = "O nome da tarefa é obrigatório")
    private String nome;

    private Date prazoFinal;

    @NotBlank(message = "O status da tarefa é obrigatório")
    private String status;

    private String comentario;

    private String descricao;

    private String projetoId;

    private List<String> anexo;

    private List<String> usuariosIds;
}
