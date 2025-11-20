package com.syntax.avisos_service.modelo.conversor;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;

/**
 * Conversor de Aviso entidade para DTO
 */
public class ConversorAvisoDTO {
    
    public static AvisoDTO converterParaDTO(Aviso aviso) {
        if (aviso == null) {
            return null;
        }
        
        AvisoDTO dto = new AvisoDTO();
        dto.setId(aviso.getId());
        dto.setTipo(aviso.getTipo());
        dto.setMensagem(aviso.getMensagem());
        dto.setData(aviso.getData());
        dto.setVisualizado(aviso.getVisualizado());
        dto.setUsuarioId(aviso.getUsuarioId());
        dto.setTarefaId(aviso.getTarefaId());
        dto.setProjetoId(aviso.getProjetoId());
        dto.setAutorId(aviso.getAutorId());
        dto.setAutorNome(aviso.getAutorNome());
        
        return dto;
    }
}
