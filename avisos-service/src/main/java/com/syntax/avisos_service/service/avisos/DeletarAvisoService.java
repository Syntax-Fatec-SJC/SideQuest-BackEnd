package com.syntax.avisos_service.service.avisos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.repositorio.AvisoRepositorio;

/**
 * Serviço para deletar aviso
 * Responsabilidade: coordenar o processo de deleção de avisos
 */
@Service
public class DeletarAvisoService {
    
    private static final Logger logger = LoggerFactory.getLogger(DeletarAvisoService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    @Autowired
    private BuscarAvisoPorIdService buscarAvisoPorIdService;
    
    public void executar(String avisoId) {
        logger.info("🗑️ Deletando aviso. ID: {}", avisoId);
        
        // Valida existência (lança exceção se não existir)
        buscarAvisoPorIdService.executar(avisoId);
        
        // Deleta
        avisoRepositorio.deleteById(avisoId);
        
        logger.info("✅ Aviso deletado com sucesso");
    }
}
