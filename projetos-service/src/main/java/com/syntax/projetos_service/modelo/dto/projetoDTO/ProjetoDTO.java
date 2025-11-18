package com.syntax.projetos_service.modelo.dto.projetoDTO;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * DTO para transferência de dados de Projeto
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
