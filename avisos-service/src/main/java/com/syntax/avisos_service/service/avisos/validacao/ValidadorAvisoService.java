package com.syntax.avisos_service.service.avisos.validacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;

/**
 * Serviço responsável por validações de regras de negócio de avisos
 */
@Service
public class ValidadorAvisoService {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidadorAvisoService.class);
    
    /**
     * Valida se os dados obrigatórios estão presentes
     */
    public void validarDadosObrigatorios(AvisoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados do aviso não podem ser nulos");
        }
        
        if (dto.getUsuarioId() == null || dto.getUsuarioId().isBlank()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }
        
        if (dto.getTipo() == null || dto.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo do aviso é obrigatório");
        }
        
        if (dto.getMensagem() == null || dto.getMensagem().isBlank()) {
            throw new IllegalArgumentException("Mensagem do aviso é obrigatória");
        }
        
        logger.debug("✓ Validação de dados obrigatórios concluída");
    }
    
    /**
     * Valida tipo do aviso
     */
    public void validarTipoAviso(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Tipo do aviso não pode ser vazio");
        }
        
        // Tipos válidos: urgente, edicao, novo, exclusao, atribuicao
        String tipoLowerCase = tipo.toLowerCase();
        if (!tipoLowerCase.equals("urgente") && 
            !tipoLowerCase.equals("edicao") && 
            !tipoLowerCase.equals("novo") &&
            !tipoLowerCase.equals("exclusao") &&
            !tipoLowerCase.equals("atribuicao")) {
            
            logger.warn("⚠️ Tipo de aviso não reconhecido: {}", tipo);
        }
    }
    
    /**
     * Valida se o aviso está relacionado a pelo menos uma entidade (tarefa ou projeto)
     */
    public void validarRelacionamentoEntidade(AvisoDTO dto) {
        if ((dto.getTarefaId() == null || dto.getTarefaId().isBlank()) && 
            (dto.getProjetoId() == null || dto.getProjetoId().isBlank())) {
            
            logger.debug("ℹ️ Aviso sem relacionamento direto com tarefa ou projeto");
        }
    }
}
