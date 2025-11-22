package com.syntax.avisos_service.service.avisos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.modelo.conversor.ConversorAviso;
import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;
import com.syntax.avisos_service.repositorio.AvisoRepositorio;
import com.syntax.avisos_service.service.avisos.util.AvisoUtil;

/**
 * Serviço para marcar aviso como visualizado
 * Responsabilidade: coordenar o processo de marcação de avisos como lidos
 */
@Service
public class MarcarComoVisualizadoService {
    
    private static final Logger logger = LoggerFactory.getLogger(MarcarComoVisualizadoService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    @Autowired
    private BuscarAvisoPorIdService buscarAvisoPorIdService;
    
    @Autowired
    private AvisoUtil avisoUtil;
    
    public AvisoDTO executar(String avisoId) {
        logger.info("👁️ Marcando aviso como visualizado. ID: {}", avisoId);
        
        // Busca o aviso (lança exceção se não existir)
        Aviso aviso = buscarAvisoPorIdService.executar(avisoId);
        
        // Verifica se já estava visualizado
        if (avisoUtil.isVisualizado(aviso)) {
            logger.debug("ℹ️ Aviso já estava visualizado");
        }
        
        // Marca como visualizado
        aviso.setVisualizado(true);
        
        // Salva
        Aviso avisoAtualizado = avisoRepositorio.save(aviso);
        
        logger.info("✅ Aviso marcado como visualizado");
        
        return ConversorAviso.converterParaDTO(avisoAtualizado);
    }
}
