package com.syntax.tarefas_service.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.client.LixeiraClient;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.RecebimentoLixeiraDTO;
import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.modelo.entidade.Tarefa;
import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DeletarTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private LixeiraClient lixeiraClient;

    public void executar(String id) {
        if (!tarefaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa nao encontrada");
        }

        Tarefa tarefa = tarefaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa nao encontrada"));

        String userId = extrairDoRequest("X-User-Id");
        String userEmail = extrairDoRequest("X-User-Email");

        try {
            RecebimentoLixeiraDTO lixeira = new RecebimentoLixeiraDTO();
            lixeira.setTipoItem("TAREFA");
            lixeira.setItemId(id);
            lixeira.setNomeItem(tarefa.getNome());
            lixeira.setStatus("NA_LIXEIRA");
            lixeira.setDeletadoPor(userId);
            lixeira.setEmailDeletadoPor(userEmail);
            lixeira.setDados(converterParaDTO(tarefa));

            lixeiraClient.enviarParaLixeira(lixeira);
            System.out.println("Tarefa enviada para lixeira: " + id);
        } catch (Exception e) {
            System.err.println("Erro ao enviar tarefa para lixeira: " + e.getMessage());
            e.printStackTrace();
        }

        tarefaRepositorio.deleteById(id);
    }

    public void deletarPermanentemente(String id) {
        if (!tarefaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa nao encontrada");
        }
        tarefaRepositorio.deleteById(id);
    }

    private TarefaDTO converterParaDTO(Tarefa tarefa) {
        TarefaDTO dto = new TarefaDTO();
        dto.setId(tarefa.getId());
        dto.setNome(tarefa.getNome());
        dto.setDescricao(tarefa.getDescricao());
        dto.setStatus(tarefa.getStatus());
        dto.setPrazoFinal(tarefa.getPrazoFinal());
        dto.setComentario(tarefa.getComentario());
        dto.setProjetoId(tarefa.getProjetoId());
        dto.setAnexos(tarefa.getAnexos());
        dto.setUsuarioIds(tarefa.getUsuarioIds());
        return dto;
    }

    private String extrairDoRequest(String atributo) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Object valor = request.getAttribute(atributo);
            return valor != null ? valor.toString() : "desconhecido";
        }
        return "desconhecido";
    }
}
