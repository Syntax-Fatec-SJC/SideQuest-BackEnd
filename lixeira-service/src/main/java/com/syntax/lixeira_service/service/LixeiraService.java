package com.syntax.lixeira_service.service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntax.lixeira_service.client.ProjetosClient;
import com.syntax.lixeira_service.client.TarefasClient;
import com.syntax.lixeira_service.conversor.ConversorItemLixeira;
import com.syntax.lixeira_service.modelo.*;
import com.syntax.lixeira_service.modelo.ItemLixeira.*;
import com.syntax.lixeira_service.repositorio.ItemLixeiraRepositorio;

@Service
public class LixeiraService {

    private static final Logger logger = LoggerFactory.getLogger(LixeiraService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ItemLixeiraRepositorio lixeiraRepositorio;

    @Autowired
    private TarefasClient tarefasClient;

    @Autowired
    private ProjetosClient projetosClient;

    @Value("${lixeira.dias-retencao:30}")
    private int diasRetencao;

    // Receber item deletado
    public ItemLixeiraDTO receberItemDeletado(RecebimentoLixeiraDTO recebimento) {
        ItemLixeira item = new ItemLixeira();
        if ("TAREFA".equalsIgnoreCase(recebimento.getTipoItem())) {
            item.setTipoItem(TipoItem.TAREFA); 
        }else if ("PROJETO".equalsIgnoreCase(recebimento.getTipoItem())) {
            item.setTipoItem(TipoItem.PROJETO); 
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo inválido");
        }

        item.setItemId(recebimento.getItemId());
        item.setNomeItem(recebimento.getNomeItem());
        item.setDeletadoPor(recebimento.getDeletadoPor());
        item.setEmailDeletadoPor(recebimento.getEmailDeletadoPor());
        item.setDeletadoEm(new Date());
        item.setStatus(StatusLixeira.NA_LIXEIRA);

        try {
            item.setDadosOriginais(objectMapper.writeValueAsString(recebimento.getDados()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao serializar dados");
        }

        ItemLixeira salvo = lixeiraRepositorio.save(item);
        return ConversorItemLixeira.converter(salvo, false);
    }

    // Listagem
    public List<ItemLixeiraDTO> listarTodos() {
        return ConversorItemLixeira.converter(lixeiraRepositorio.findByStatus(StatusLixeira.NA_LIXEIRA), false);
    }

    public List<ItemLixeiraDTO> listarTarefas() {
        return ConversorItemLixeira.converter(
                lixeiraRepositorio.findByTipoItemAndStatus(TipoItem.TAREFA, StatusLixeira.NA_LIXEIRA), false);
    }

    public List<ItemLixeiraDTO> listarProjetos() {
        return ConversorItemLixeira.converter(
                lixeiraRepositorio.findByTipoItemAndStatus(TipoItem.PROJETO, StatusLixeira.NA_LIXEIRA), false);
    }

    public List<ItemLixeiraDTO> listarPorUsuario(String userId) {
        return ConversorItemLixeira.converter(
                lixeiraRepositorio.findByDeletadoPorAndStatus(userId, StatusLixeira.NA_LIXEIRA), false);
    }

    public ItemLixeiraDTO buscarPorId(String id) {
        ItemLixeira item = lixeiraRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
        return ConversorItemLixeira.converter(item, true);
    }

    public long contarItens() {
        return lixeiraRepositorio.countByStatus(StatusLixeira.NA_LIXEIRA);
    }

    // RESTAURAÇÃO
    public ItemLixeiraDTO restaurar(String lixeiraId) {
        ItemLixeira item = lixeiraRepositorio.findById(lixeiraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado na lixeira"));

        if (item.getStatus() != StatusLixeira.NA_LIXEIRA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item não está na lixeira");
        }

        try {
            Map<String, Object> dadosOriginais = objectMapper.readValue(
                    item.getDadosOriginais(),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));

            if (item.getTipoItem() == TipoItem.TAREFA) {
                restaurarTarefa(dadosOriginais); 
            }else if (item.getTipoItem() == TipoItem.PROJETO) {
                restaurarProjeto(dadosOriginais);
            }

            item.setStatus(StatusLixeira.RESTAURADO);
            item.setRestauradoEm(new Date());
            lixeiraRepositorio.save(item);
            lixeiraRepositorio.deleteById(lixeiraId);

            return ConversorItemLixeira.converter(item, false);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao restaurar item: " + e.getMessage());
        }
    }

    private void restaurarTarefa(Map<String, Object> dados) {
        try {
            tarefasClient.restaurarTarefa(dados);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao restaurar tarefa no serviço de tarefas");
        }
    }

    private void restaurarProjeto(Map<String, Object> dados) {
        try {
            projetosClient.restaurarProjeto(dados);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao restaurar projeto no serviço de projetos");
        }
    }

    // DELETAR PERMANENTEMENTE
    public void deletarPermanentemente(String lixeiraId) {
        ItemLixeira item = lixeiraRepositorio.findById(lixeiraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));

        try {
            if (item.getTipoItem() == TipoItem.TAREFA) {
                tarefasClient.deletarPermanente(item.getItemId()); 
            }else if (item.getTipoItem() == TipoItem.PROJETO) {
                projetosClient.deletarPermanente(item.getItemId());
            }
        } catch (Exception e) {
            // continua
        }

        lixeiraRepositorio.deleteById(lixeiraId);
    }

    // ESVAZIAR
    public void esvaziarTarefas() {
        lixeiraRepositorio.deleteAll(lixeiraRepositorio.findByTipoItemAndStatus(TipoItem.TAREFA, StatusLixeira.NA_LIXEIRA));
    }

    public void esvaziarProjetos() {
        lixeiraRepositorio.deleteAll(lixeiraRepositorio.findByTipoItemAndStatus(TipoItem.PROJETO, StatusLixeira.NA_LIXEIRA));
    }

    public void esvaziarTudo() {
        lixeiraRepositorio.deleteAll(lixeiraRepositorio.findByStatus(StatusLixeira.NA_LIXEIRA));
    }

    // LIMPEZA AUTOMÁTICA
    @Scheduled(cron = "${lixeira.cron-limpeza:0 0 2 * * *}")
    public void limparAutomaticamente() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -diasRetencao);
        Date dataLimite = cal.getTime();
        List<ItemLixeira> antigos = lixeiraRepositorio.findByDeletadoEmBeforeAndStatus(dataLimite, StatusLixeira.NA_LIXEIRA);
        antigos.forEach(item -> deletarPermanentemente(item.getId()));
    }

    public int limparAgora() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -diasRetencao);
        Date dataLimite = cal.getTime();
        List<ItemLixeira> antigos = lixeiraRepositorio.findByDeletadoEmBeforeAndStatus(dataLimite, StatusLixeira.NA_LIXEIRA);
        antigos.forEach(item -> lixeiraRepositorio.deleteById(item.getId()));
        return antigos.size();
    }

    public String obterEstatisticas() {
        long total = lixeiraRepositorio.countByStatus(StatusLixeira.NA_LIXEIRA);
        long tarefas = lixeiraRepositorio.countByTipoItemAndStatus(TipoItem.TAREFA, StatusLixeira.NA_LIXEIRA);
        long projetos = lixeiraRepositorio.countByTipoItemAndStatus(TipoItem.PROJETO, StatusLixeira.NA_LIXEIRA);
        return String.format("Total: %d | Tarefas: %d | Projetos: %d", total, tarefas, projetos);
    }
}
