package com.syntax.tarefas_service.modelo.dto.tarefaDTO;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * DTO para transferência de dados de Tarefa
 */
@Data
public class TarefaDTO {
    private String id;
    private String nome;
    private Date prazoFinal;
    private String status;
    private String comentario;
    private String descricao;
    private String projetoId;
    private List<String> anexos;
    private List<String> usuarioIds;
}
