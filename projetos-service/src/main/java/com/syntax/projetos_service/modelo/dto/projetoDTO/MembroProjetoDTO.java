package com.syntax.projetos_service.modelo.dto.projetoDTO;

import java.util.List;

import lombok.Data;

/**
 * DTO para gerenciar membros de um projeto
 */
@Data
public class MembroProjetoDTO {
    private List<String> usuarioIds;
}
