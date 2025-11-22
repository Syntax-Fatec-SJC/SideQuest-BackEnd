package com.syntax.tarefas_service.service.tarefas.atualizarTarefa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.AvisosClient;
import com.syntax.tarefas_service.client.ProjetosClient;
import com.syntax.tarefas_service.client.UsuariosClient;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefa;
import com.syntax.tarefas_service.modelo.conversor.ConversorTarefaDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para atualização completa de tarefa
 */
@Service
public class AtualizarTarefaCompletaService {

    private static final Logger logger = LoggerFactory.getLogger(AtualizarTarefaCompletaService.class);

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private ProjetosClient projetosClient;

    @Autowired
    private AvisosClient avisosClient;

    @Autowired
    private UsuariosClient usuariosClient;

    public TarefaDTO executar(String id, TarefaDTO dto, String autorId, String autorNome) {
        Tarefa existente = tarefaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        String projetoId = dto.getProjetoId();
        if (projetoId == null || projetoId.isBlank()) {
            projetoId = existente.getProjetoId();
        }

        ProjetoDTO projeto = projetosClient.buscarProjeto(projetoId);

        // Valida se a data da tarefa não excede a data do projeto
        validarPrazoTarefa(dto.getPrazoFinal(), projeto.getPrazoFinal());

        List<String> usuarioIds = normalizarLista(dto.getUsuarioIds());
        validarUsuariosDoProjeto(usuarioIds, projeto);

        // Identifica novos membros adicionados à tarefa
        List<String> usuariosAntigos = normalizarLista(existente.getUsuarioIds());
        Set<String> usuariosAntigosSet = new HashSet<>(usuariosAntigos);
        List<String> novosMembros = new ArrayList<>();
        
        for (String usuarioId : usuarioIds) {
            if (!usuariosAntigosSet.contains(usuarioId)) {
                novosMembros.add(usuarioId);
            }
        }

        Tarefa atualizado = new ConversorTarefaDTO().converter(dto);
        atualizado.setId(id);
        atualizado.setProjetoId(projetoId);
        atualizado.setUsuarioIds(usuarioIds);

        Tarefa salvo = tarefaRepositorio.save(atualizado);
        
        if (autorNome != null && !autorNome.isBlank()) {
            // Cria avisos específicos para NOVOS membros adicionados à tarefa
            if (!novosMembros.isEmpty()) {
                logger.info("🔔 Criando avisos para {} novos membros adicionados à tarefa {}", 
                    novosMembros.size(), salvo.getId());
                
                for (String membroAdicionadoId : novosMembros) {
                    String membroAdicionadoNome = usuariosClient.buscarNomeUsuario(membroAdicionadoId);
                    
                    if (membroAdicionadoNome == null) {
                        membroAdicionadoNome = "um usuário";
                    }
                    
                    logger.info("🔔 Novo membro adicionado: {} ({})", membroAdicionadoId, membroAdicionadoNome);
                    
                    // Para cada membro adicionado, cria avisos para todos os membros da tarefa
                    for (String membroId : usuarioIds) {
                        if (membroId.equals(membroAdicionadoId)) {
                            // Aviso para o próprio membro que foi adicionado
                            if (membroAdicionadoId.equals(autorId)) {
                                // Se o autor se adicionou, mostra como edição
                                avisosClient.criarAvisoTarefaEditada(
                                    salvo.getId(), 
                                    salvo.getProjetoId(), 
                                    membroId, 
                                    autorId,
                                    autorNome
                                );
                            } else {
                                // Se foi adicionado por outro usuário, mostra como atribuição
                                avisosClient.criarAvisoNovaTarefa(
                                    salvo.getId(), 
                                    salvo.getProjetoId(), 
                                    membroId, 
                                    autorId,
                                    autorNome
                                );
                            }
                        } else {
                            // Aviso para outros membros sobre quem foi adicionado
                            avisosClient.criarAvisoMembroAdicionadoTarefa(
                                salvo.getId(), 
                                salvo.getProjetoId(), 
                                membroId, 
                                autorId,
                                autorNome,
                                membroAdicionadoId,
                                membroAdicionadoNome
                            );
                        }
                    }
                }
            }
            
            // Cria avisos de edição para membros que já estavam na tarefa
            List<String> membrosExistentes = new ArrayList<>(usuariosAntigos);
            membrosExistentes.retainAll(usuarioIds); // Mantém apenas os que ainda estão
            
            if (!membrosExistentes.isEmpty()) {
                logger.info("🔔 Criando avisos de edição para {} membros existentes", membrosExistentes.size());
                
                for (String usuarioId : membrosExistentes) {
                    avisosClient.criarAvisoTarefaEditada(
                        salvo.getId(), 
                        salvo.getProjetoId(), 
                        usuarioId, 
                        autorId,
                        autorNome
                    );
                }
            }
            
            // Cria aviso de edição também para o autor se ele não estiver na lista
            if (autorId != null && !autorId.isBlank() && !usuarioIds.contains(autorId)) {
                logger.info("🔔 Criando aviso de edição para o autor: {} ({})", autorId, autorNome);
                avisosClient.criarAvisoTarefaEditada(
                    salvo.getId(),
                    salvo.getProjetoId(),
                    autorId,
                    autorId,
                    autorNome
                );
            }
        } else {
            logger.warn("⚠️ AutorNome nulo ou vazio - não criando avisos");
        }
        
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
