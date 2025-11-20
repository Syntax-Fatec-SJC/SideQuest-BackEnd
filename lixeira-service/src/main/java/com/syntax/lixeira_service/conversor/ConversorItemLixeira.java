package com.syntax.lixeira_service.conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntax.lixeira_service.modelo.ItemLixeira;
import com.syntax.lixeira_service.modelo.ItemLixeiraDTO;

public class ConversorItemLixeira {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ItemLixeiraDTO converter(ItemLixeira item, boolean incluirDados) {
        if (item == null) {
            return null;
        }

        ItemLixeiraDTO dto = new ItemLixeiraDTO();
        dto.setId(item.getId());
        dto.setTipoItem(item.getTipoItem());
        dto.setItemId(item.getItemId());
        dto.setNomeItem(item.getNomeItem());
        dto.setDeletadoPor(item.getDeletadoPor());
        dto.setEmailDeletadoPor(item.getEmailDeletadoPor());
        dto.setDeletadoEm(item.getDeletadoEm());
        dto.setRestauradoEm(item.getRestauradoEm());
        dto.setStatus(item.getStatus());

        if (incluirDados && item.getDadosOriginais() != null) {
            try {
                Object dados = objectMapper.readValue(item.getDadosOriginais(), Object.class);
                dto.setDadosOriginais(dados);
            } catch (Exception e) {
                dto.setDadosOriginais(item.getDadosOriginais());
            }
        }
        return dto;
    }

    public static List<ItemLixeiraDTO> converter(List<ItemLixeira> itens, boolean incluirDados) {
        if (itens == null || itens.isEmpty()) {
            return new ArrayList<>();
        }
        return itens.stream()
                .map(item -> converter(item, incluirDados))
                .collect(Collectors.toList());
    }
}
