package com.syntax.avisos_service.modelo.dto.RespostaDTO;

import lombok.Data;

/**
 * DTO padrão para respostas de sucesso
 */
@Data
public class RespostaDTO {
    private String mensagem;
    
    public RespostaDTO(String mensagem) {
        this.mensagem = mensagem;
    }
}
