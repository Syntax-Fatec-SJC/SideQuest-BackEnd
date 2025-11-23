package com.syntax.avisos_service.service.avisos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.excecao.personalizado.RecursoNaoEncontradoException;
import com.syntax.avisos_service.modelo.entidade.Aviso;
import com.syntax.avisos_service.repositorio.AvisoRepositorio;

/**
 * Serviço para buscar aviso por ID
 * Responsabilidade: buscar e validar existência de avisos
 */
@Service
public class BuscarAvisoPorIdService {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarAvisoPorIdService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    /**
     * Busca aviso por ID, lançando exceção se não encontrado
     */
    public Aviso executar(String avisoId) {
        logger.debug("🔍 Buscando aviso. ID: {}", avisoId);
        
        return avisoRepositorio.findById(avisoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aviso não encontrado"));
    }
    
    /**
     * Verifica se o aviso existe
     */
    public boolean existe(String avisoId) {
        return avisoRepositorio.existsById(avisoId);
    }
}
