package com.syntax.tarefas_service.modelo.dto.avisoDTO;

import java.util.Date;

import lombok.Data;

/**
 * DTO para criação de avisos
 */
@Data
public class AvisoDTO {
    private String id;
    private String tipo; // urgente, edicao, novo
    private String mensagem;
    private Date data;
    private Boolean visualizado;
    private String usuarioId;
    private String tarefaId;
    private String projetoId;
    private String autorId;
    private String autorNome;
    private String membroAdicionadoId;
    private String membroAdicionadoNome;
}
