package com.syntax.sidequest_backend.modelo.conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.syntax.sidequest_backend.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;

public class ConversorProjeto {
    public static ProjetoDTO paraDTO(Projeto projeto) {
        if (projeto == null) {
            return null;
        }

        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setNome(projeto.getNome());
        dto.setStatus(projeto.getStatus());
        dto.setDescricao(projeto.getDescricao());
        dto.setPrazoFinal(projeto.getPrazoFinal());
        dto.setUsuarioIds(copiarLista(projeto.getUsuarioIds()));
        return dto;
    }

    public static List<ProjetoDTO> paraDTO(List<Projeto> projetos) {
        if (projetos == null || projetos.isEmpty()) {
            return new ArrayList<>();
        }

        return projetos.stream()
                .filter(Objects::nonNull)
                .map(ConversorProjeto::paraDTO)
                .collect(Collectors.toList());
    }

    
    private static List<String> copiarLista(List<String> origem) {
        if (origem == null || origem.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(origem);
    }
}
