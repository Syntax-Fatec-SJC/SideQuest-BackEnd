package com.syntax.avisos_service.modelo.conversor;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;

/**
 * Conversor de DTO para Aviso entidade
 */
public class ConversorAviso {
    
    public static Aviso converterParaEntidade(AvisoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Aviso aviso = new Aviso();
        aviso.setId(dto.getId());
        aviso.setTipo(dto.getTipo());
        aviso.setMensagem(dto.getMensagem());
        aviso.setData(dto.getData());
        aviso.setVisualizado(dto.getVisualizado() != null ? dto.getVisualizado() : false);
        aviso.setUsuarioId(dto.getUsuarioId());
        aviso.setTarefaId(dto.getTarefaId());
        aviso.setProjetoId(dto.getProjetoId());
        aviso.setAutorId(dto.getAutorId());
        aviso.setAutorNome(dto.getAutorNome());
        
        return aviso;
    }
}
