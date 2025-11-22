package com.syntax.avisos_service.service.avisos;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.modelo.conversor.ConversorAviso;
import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;
import com.syntax.avisos_service.repositorio.AvisoRepositorio;

/**
 * Serviço para listar avisos de um usuário
 */
@Service
public class ListarAvisosService {
    
    private static final Logger logger = LoggerFactory.getLogger(ListarAvisosService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    /**
     * Lista todos os avisos de um usuário
     */
    public List<AvisoDTO> listarTodos(String usuarioId) {
        logger.info("📋 Listando todos os avisos do usuário: {}", usuarioId);
        
        List<Aviso> avisos = avisoRepositorio.findByUsuarioIdOrderByDataDesc(usuarioId);
        
        logger.info("✅ {} avisos encontrados", avisos.size());
        
        return avisos.stream()
                .map(ConversorAviso::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Lista apenas avisos não visualizados de um usuário
     */
    public List<AvisoDTO> listarNaoVisualizados(String usuarioId) {
        logger.info("📋 Listando avisos não visualizados do usuário: {}", usuarioId);
        
        List<Aviso> avisos = avisoRepositorio.findByUsuarioIdAndVisualizadoOrderByDataDesc(usuarioId, false);
        
        logger.info("✅ {} avisos não visualizados encontrados", avisos.size());
        
        return avisos.stream()
                .map(ConversorAviso::converterParaDTO)
                .collect(Collectors.toList());
    }
}
