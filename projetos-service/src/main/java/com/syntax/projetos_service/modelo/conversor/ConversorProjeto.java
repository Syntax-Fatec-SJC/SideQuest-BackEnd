package com.syntax.projetos_service.modelo.conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;

/**
 * Conversor de Projeto para ProjetoDTO
 */
public class ConversorProjeto {

    public static ProjetoDTO converter(Projeto projeto) {
        if (projeto == null) {
            return null;
        }

        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setStatus(projeto.getStatus());
        dto.setNome(projeto.getNome());
        dto.setDescricao(projeto.getDescricao());
        dto.setPrazoFinal(projeto.getPrazoFinal());
        dto.setUsuarioIds(copiarLista(projeto.getUsuarioIds()));
        return dto;
    }

    public static List<ProjetoDTO> converter(List<Projeto> projetos) {
        if (projetos == null || projetos.isEmpty()) {
            return new ArrayList<>();
        }

        return projetos.stream()
                .filter(Objects::nonNull)
                .map(ConversorProjeto::converter)
                .collect(Collectors.toList());
    }

    private static List<String> copiarLista(List<String> origem) {
        if (origem == null || origem.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(origem);
    }
}
