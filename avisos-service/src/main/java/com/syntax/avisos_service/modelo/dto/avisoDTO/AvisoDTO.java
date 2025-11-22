package com.syntax.avisos_service.modelo.dto.avisoDTO;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO para criação e transferência de dados de Aviso
 */
@Data
public class AvisoDTO {
    
    private String id;
    
    @NotBlank(message = "O tipo do aviso é obrigatório")
    private String tipo; // urgente, edicao, novo
    
    @NotBlank(message = "A mensagem do aviso é obrigatória")
    private String mensagem;
    
    @NotNull(message = "A data do aviso é obrigatória")
    private Date data;
    
    private Boolean visualizado;
    
    @NotBlank(message = "O ID do usuário é obrigatório")
    private String usuarioId;
    
    private String tarefaId;
    
    private String projetoId;
    
    private String autorId;
    
    private String autorNome;
    
    private String membroAdicionadoId;
    
    private String membroAdicionadoNome;
}
