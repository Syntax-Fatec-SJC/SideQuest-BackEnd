package com.syntax.tarefas_service.service.tarefas;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.AvisosClient;
import com.syntax.tarefas_service.client.ProjetosClient;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefaDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para cadastrar nova tarefa
 */
@Service
public class CadastrarTarefaService {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarTarefaService.class);

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private ProjetosClient projetosClient;

    @Autowired
    private AvisosClient avisosClient;

    public TarefaDTO executar(TarefaDTO dto, String autorId, String autorNome) {
        if (dto.getProjetoId() == null || dto.getProjetoId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProjetoId é obrigatório");
        }

        // Busca projeto no Projetos-Service
        ProjetoDTO projeto = projetosClient.buscarProjeto(dto.getProjetoId());

        // Valida se a data da tarefa não excede a data do projeto
        validarPrazoTarefa(dto.getPrazoFinal(), projeto.getPrazoFinal());

        List<String> usuarioIds = normalizarLista(dto.getUsuarioIds());
        validarUsuariosDoProjeto(usuarioIds, projeto);

        Tarefa tarefa = new ConversorTarefaDTO().converter(dto);
        tarefa.setUsuarioIds(usuarioIds);

        Tarefa salva = tarefaRepositorio.save(tarefa);
        
        logger.info("📝 Tarefa salva: {} - Usuários: {} - AutorId: {} - AutorNome: {}", 
            salva.getId(), usuarioIds, autorId, autorNome);
        
        // Cria avisos para TODOS os usuários atrelados à tarefa (incluindo o autor)
        if (autorNome != null && !autorNome.isBlank()) {
            logger.info("🔔 Iniciando criação de avisos - {} usuários", usuarioIds.size());
            for (String usuarioId : usuarioIds) {
                logger.info("🔔 Criando aviso para usuário: {} (autor: {} - {})", usuarioId, autorId, autorNome);
                avisosClient.criarAvisoNovaTarefa(
                    salva.getId(), 
                    salva.getProjetoId(), 
                    usuarioId, 
                    autorId,
                    autorNome
                );
            }
            
            // Cria aviso também para o autor
            if (autorId != null && !autorId.isBlank() && !usuarioIds.contains(autorId)) {
                logger.info("🔔 Criando aviso para o autor: {} ({})", autorId, autorNome);
                avisosClient.criarAvisoNovaTarefa(
                    salva.getId(), 
                    salva.getProjetoId(), 
                    autorId, 
                    autorId,
                    autorNome
                );
            }
        } else {
            logger.warn("⚠️ AutorNome nulo ou vazio - não criando avisos");
        }
        
        return ConversorTarefa.converter(salva);
    }

    private List<String> normalizarLista(List<String> origem) {
        return origem == null ? new ArrayList<>() : new ArrayList<>(origem);
    }

    private void validarUsuariosDoProjeto(List<String> usuarioIds, ProjetoDTO projeto) {
        if (usuarioIds.isEmpty()) {
            return;
        }

        List<String> membros = projeto.getUsuarioIds();
        if (membros == null || membros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projeto não possui membros cadastrados");
        }

        for (String usuarioId : usuarioIds) {
            if (!membros.contains(usuarioId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Usuário " + usuarioId + " não está vinculado ao projeto informado");
            }
        }
    }

    private void validarPrazoTarefa(java.util.Date prazoTarefa, java.util.Date prazoProjeto) {
        if (prazoTarefa == null || prazoProjeto == null) {
            return; // Se algum dos prazos não estiver definido, não valida
        }

        if (prazoTarefa.after(prazoProjeto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "O prazo final da tarefa não pode ser posterior ao prazo final do projeto");
        }
    }
}
