package com.syntax.usuario_service.modelo.dto.usuarioDTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar tarefas com entregas próximas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProximasEntregasDTO {
    
    private String tarefaId;
    private String titulo;
    private String descricao;
    private String projetoId;
    private String projetoTitulo;
    private LocalDateTime dataEntrega;
    private String status;
    private List<String> responsaveis;
}
