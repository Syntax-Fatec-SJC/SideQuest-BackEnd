package com.syntax.tarefas_service.modelo.dto.RespostaDTO;

import java.time.LocalDateTime;

/**
 * DTO para padronizar respostas de erro da API
 */
public record ErroRespostaDTO(
    Integer status,
    String erro,
    String mensagem,
    LocalDateTime timestamp
) {
    /**
     * Construtor auxiliar que define o timestamp automaticamente
     */
    public ErroRespostaDTO(Integer status, String erro, String mensagem) {
        this(status, erro, mensagem, LocalDateTime.now());
    }
}
