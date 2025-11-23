package com.syntax.projetos_service.service.projetos;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.client.AvisosClient;
import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.conversor.ConversorProjetoDTO;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para cadastrar novo projeto
 */
@Service
public class CadastrarProjetoService {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarProjetoService.class);

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private AvisosClient avisosClient;

    public ProjetoDTO executar(ProjetoDTO dto, String usuarioIdCriador, String autorNome) {
        if (usuarioIdCriador == null || usuarioIdCriador.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "ID do usuário criador é obrigatório");
        }

        Projeto projeto = new ConversorProjetoDTO().converter(dto);
        
        // Adicionar criador como primeiro membro
        if (projeto.getUsuarioIds() == null) {
            projeto.setUsuarioIds(new ArrayList<>());
        }
        if (!projeto.getUsuarioIds().contains(usuarioIdCriador)) {
            projeto.getUsuarioIds().add(0, usuarioIdCriador);
        }

        Projeto salvo = projetoRepositorio.save(projeto);
        
        // Criar avisos para todos os membros do projeto
        if (autorNome != null && !autorNome.isBlank()) {
            logger.info("🔔 Iniciando criação de avisos para novo projeto - {} membros", salvo.getUsuarioIds().size());
            for (String usuarioId : salvo.getUsuarioIds()) {
                logger.info("🔔 Criando aviso de novo projeto para usuário: {}", usuarioId);
                avisosClient.criarAvisoNovoMembroProjeto(
                    salvo.getId(),
                    usuarioId,
                    usuarioIdCriador,
                    autorNome
                );
            }
        } else {
            logger.warn("⚠️ AutorNome nulo ou vazio - não criando avisos para novo projeto");
        }
        
        return ConversorProjeto.converter(salvo);
    }
}
