package com.syntax.sidequest_backend.modelo.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para atualização de responsáveis de uma tarefa.
 * 
 * Este DTO é usado exclusivamente para o endpoint PATCH /tarefas/{id}/responsaveis
 * permitindo a atualização parcial apenas dos usuários responsáveis.
 * 
 * @author SideQuest Team
 * @see com.syntax.sidequest_backend.controller.TarefaController#atualizarResponsaveis
 */
public class AtualizarResponsaveisDTO {
    
    @NotNull(message = "A lista de IDs de usuários não pode ser nula")
    private List<String> usuarioIds;

    /**
     * Construtor padrão necessário para deserialização JSON.
     */
    public AtualizarResponsaveisDTO() {
    }

    /**
     * Construtor com parâmetros para facilitar criação em testes.
     * 
     * @param usuarioIds Lista de IDs dos usuários responsáveis
     */
    public AtualizarResponsaveisDTO(List<String> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }

    /**
     * Retorna a lista de IDs dos usuários responsáveis.
     * 
     * @return Lista de IDs dos usuários
     */
    public List<String> getUsuarioIds() {
        return usuarioIds;
    }

    /**
     * Define a lista de IDs dos usuários responsáveis.
     * 
     * @param usuarioIds Lista de IDs dos usuários
     */
    public void setUsuarioIds(List<String> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }

    @Override
    public String toString() {
        return "AtualizarResponsaveisDTO{" +
                "usuarioIds=" + usuarioIds +
                '}';
    }
}
