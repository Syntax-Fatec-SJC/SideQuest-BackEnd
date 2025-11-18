package com.syntax.projetos_service.modelo.conversor;

import java.util.ArrayList;
import java.util.List;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;

/**
 * Conversor de ProjetoDTO para Projeto
 */
public class ConversorProjetoDTO {

    public Projeto converter(ProjetoDTO dto) {
        if (dto == null) {
            return null;
        }

        Projeto projeto = new Projeto();
        projeto.setId(dto.getId());
        projeto.setStatus(dto.getStatus());
        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());
        projeto.setPrazoFinal(dto.getPrazoFinal());
        projeto.setUsuarioIds(copiarLista(dto.getUsuarioIds()));
        return projeto;
    }

    private static List<String> copiarLista(List<String> origem) {
        if (origem == null || origem.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(origem);
    }
}
