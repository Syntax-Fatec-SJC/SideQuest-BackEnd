package com.syntax.tarefas_service.modelo.dto.tarefaDTO;

import java.util.List;

import lombok.Data;

/**
 * DTO para atualizar responsáveis de uma tarefa
 */
@Data
public class AtualizarResponsaveisDTO {
    private List<String> usuarioIds;
}
