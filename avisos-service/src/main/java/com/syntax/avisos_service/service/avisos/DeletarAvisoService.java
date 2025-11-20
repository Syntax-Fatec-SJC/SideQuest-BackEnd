package com.syntax.avisos_service.service.avisos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.excecao.personalizado.RecursoNaoEncontradoException;
import com.syntax.avisos_service.repositorio.AvisoRepositorio;

/**
 * Serviço para deletar aviso
 */
@Service
public class DeletarAvisoService {
    
    private static final Logger logger = LoggerFactory.getLogger(DeletarAvisoService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    public void executar(String avisoId) {
        logger.info("🗑️ Deletando aviso. ID: {}", avisoId);
        
        // Verifica se existe
        if (!avisoRepositorio.existsById(avisoId)) {
            throw new RecursoNaoEncontradoException("Aviso não encontrado");
        }
        
        // Deleta
        avisoRepositorio.deleteById(avisoId);
        
        logger.info("✅ Aviso deletado com sucesso");
    }
}
