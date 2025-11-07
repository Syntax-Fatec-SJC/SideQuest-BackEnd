package com.syntax.tarefas_service.modelo.dto.tarefaDTO;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * DTO para representar um Projeto (vindo do Projetos-Service)
 */
@Data
public class ProjetoDTO {
    private String id;
    private String status;
    private String nome;
    private String descricao;
    private Date prazoFinal;
    private List<String> usuarioIds;
}
