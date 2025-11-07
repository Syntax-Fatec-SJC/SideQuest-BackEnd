package com.syntax.tarefas_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;

/**
 * Cliente para comunicação com o Projetos-Service
 */
@Component
public class ProjetosClient {

    private final RestTemplate restTemplate;
    
    @Value("${projetos.service.url}")
    private String projetosServiceUrl;

    public ProjetosClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Busca um projeto pelo ID no Projetos-Service
     */
    public ProjetoDTO buscarProjeto(String projetoId) {
        try {
            String url = projetosServiceUrl + "/listar/projetos/" + projetoId;
            return restTemplate.getForObject(url, ProjetoDTO.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
        }
    }
}
