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
import com.syntax.avisos_service.service.avisos.validacao.ValidadorAvisoService;

/**
 * Serviço para cadastrar novo aviso
 * Responsabilidade: coordenar o processo de criação de avisos
 */
@Service
public class CadastrarAvisoService {
    
    private static final Logger logger = LoggerFactory.getLogger(CadastrarAvisoService.class);
    
    @Autowired
    private AvisoRepositorio avisoRepositorio;
    
    @Autowired
    private ValidadorAvisoService validadorAvisoService;
    
    @Autowired
    private AvisoUtil avisoUtil;
    
    public AvisoDTO executar(AvisoDTO dto) {
        logger.info("📝 Cadastrando novo aviso para usuário: {}", dto.getUsuarioId());
        
        // Valida dados de entrada
        validadorAvisoService.validarDadosObrigatorios(dto);
        validadorAvisoService.validarTipoAviso(dto.getTipo());
        
        // Converte DTO para entidade
        Aviso aviso = ConversorAviso.converterParaEntidade(dto);
        
        // Inicializa valores padrão
        avisoUtil.inicializarValoresPadrao(aviso);
        
        // Salva no banco
        Aviso avisoSalvo = avisoRepositorio.save(aviso);
        
        logger.info("✅ Aviso cadastrado com sucesso. ID: {} | {}", 
            avisoSalvo.getId(), 
            avisoUtil.formatarLogCriacao(avisoSalvo));
        
        // Retorna o DTO
        return ConversorAviso.converterParaDTO(avisoSalvo);
    }
}
