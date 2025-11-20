package com.syntax.avisos_service.service.avisos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.modelo.conversor.ConversorAviso;
import com.syntax.avisos_service.modelo.conversor.ConversorAvisoDTO;
import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.modelo.entidade.Aviso;
import com.syntax.avisos_service.repositorio.AvisoRepositorio;

/**
 * Serviço para cadastrar novo aviso
 */
@Service
public class CadastrarAvisoService {
    
    private static final Logger logger = LoggerFactory.getLogger(CadastrarAvisoService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    public AvisoDTO executar(AvisoDTO dto) {
        logger.info("📝 Cadastrando novo aviso para usuário: {}", dto.getUsuarioId());
        
        // Converte DTO para entidade
        Aviso aviso = ConversorAviso.converterParaEntidade(dto);
        
        // Garante que visualizado seja false por padrão
        if (aviso.getVisualizado() == null) {
            aviso.setVisualizado(false);
        }
        
        // Salva no banco
        Aviso avisoSalvo = avisoRepositorio.save(aviso);
        
        logger.info("✅ Aviso cadastrado com sucesso. ID: {}", avisoSalvo.getId());
        
        // Retorna o DTO
        return ConversorAvisoDTO.converterParaDTO(avisoSalvo);
    }
}
