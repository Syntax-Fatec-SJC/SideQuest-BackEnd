package com.syntax.avisos_service.service.avisos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.excecao.personalizado.RecursoNaoEncontradoException;
import com.syntax.avisos_service.modelo.conversor.ConversorAvisoDTO;
import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;
import com.syntax.avisos_service.repositorio.AvisoRepositorio;

/**
 * Serviço para marcar aviso como visualizado
 */
@Service
public class MarcarComoVisualizadoService {
    
    private static final Logger logger = LoggerFactory.getLogger(MarcarComoVisualizadoService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    public AvisoDTO executar(String avisoId) {
        logger.info("👁️ Marcando aviso como visualizado. ID: {}", avisoId);
        
        // Busca o aviso
        Aviso aviso = avisoRepositorio.findById(avisoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aviso não encontrado"));
        
        // Marca como visualizado
        aviso.setVisualizado(true);
        
        // Salva
        Aviso avisoAtualizado = avisoRepositorio.save(aviso);
        
        logger.info("✅ Aviso marcado como visualizado");
        
        return ConversorAvisoDTO.converterParaDTO(avisoAtualizado);
    }
}
