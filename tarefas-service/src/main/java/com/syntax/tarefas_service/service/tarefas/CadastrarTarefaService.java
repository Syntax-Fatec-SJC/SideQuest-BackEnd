package com.syntax.tarefas_service.service.tarefas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.ProjetosClient;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefaDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CadastrarTarefaService {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarTarefaService.class);

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private ProjetosClient projetosClient;

    public TarefaDTO executar(TarefaDTO dto) {
        if (dto.getProjetoId() == null || dto.getProjetoId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProjetoId é obrigatório");
        }

        // --- Extração de userId e userEmail ---
        String userId = null;
        String userEmail = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest currentRequest = attributes.getRequest();
            userId = (String) currentRequest.getAttribute("userId");
            userEmail = (String) currentRequest.getAttribute("userEmail");
            logger.debug("[CadastrarTarefaService] userId={}, userEmail={}", userId, userEmail);
        } else {
            logger.warn("[CadastrarTarefaService] RequestContextHolder retornou null");
        }

        // --- Busca projeto no Projetos-Service (CORRIGIDO) ---
        ProjetoDTO projeto = projetosClient.buscarProjeto(dto.getProjetoId());

        // --- Validação de usuários da tarefa ---
        List<String> usuarioIds = normalizarLista(dto.getUsuarioIds());
        validarUsuariosDoProjeto(usuarioIds, projeto);

        // --- Conversão DTO -> Entidade ---
        Tarefa tarefa = new ConversorTarefaDTO().converter(dto);
        tarefa.setProjetoId(projeto.getId());

        // --- Salva a tarefa ---
        Tarefa salvo = tarefaRepositorio.save(tarefa);

        return ConversorTarefa.converter(salvo);
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

        Set<String> membrosSet = new HashSet<>(membros);
        for (String usuarioId : usuarioIds) {
            if (!membrosSet.contains(usuarioId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Usuário " + usuarioId + " não está vinculado ao projeto");
            }
        }
    }
}
