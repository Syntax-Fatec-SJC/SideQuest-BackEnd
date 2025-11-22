package com.syntax.avisos_service.modelo.conversor;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;

/**
 * Conversor bidirecional entre Aviso e AvisoDTO
 * Centraliza toda a lógica de conversão em uma única classe
 */
public class ConversorAviso {
    
    /**
     * Converte DTO para Entidade
     */
    public static Aviso converterParaEntidade(AvisoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Aviso aviso = new Aviso();
        aviso.setId(dto.getId());
        aviso.setTipo(dto.getTipo());
        aviso.setMensagem(dto.getMensagem());
        aviso.setData(dto.getData());
        aviso.setVisualizado(Boolean.TRUE.equals(dto.getVisualizado()) ? dto.getVisualizado() : false);
        aviso.setUsuarioId(dto.getUsuarioId());
        aviso.setTarefaId(dto.getTarefaId());
        aviso.setProjetoId(dto.getProjetoId());
        aviso.setAutorId(dto.getAutorId());
        aviso.setAutorNome(dto.getAutorNome());
        aviso.setMembroAdicionadoId(dto.getMembroAdicionadoId());
        aviso.setMembroAdicionadoNome(dto.getMembroAdicionadoNome());
        
        return aviso;
    }
    
    /**
     * Converte Entidade para DTO
     */
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
        dto.setMembroAdicionadoId(aviso.getMembroAdicionadoId());
        dto.setMembroAdicionadoNome(aviso.getMembroAdicionadoNome());
        
        return dto;
    }
}
