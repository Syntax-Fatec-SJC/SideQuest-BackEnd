package com.syntax.tarefas_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.ProjetoDTO;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Cliente para comunicação com o Projetos-Service
 */
@Component
public class ProjetosClient {

    private final RestTemplate restTemplate;
    
    @Value("${projetos.service.url}")
    private String projetosServiceUrl;

    @Value("${gateway.secret}")
    private String gatewaySecret;

    public ProjetosClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Busca um projeto pelo ID no Projetos-Service
     */
    public ProjetoDTO buscarProjeto(String projetoId) {
        try {
            String url = projetosServiceUrl + "/listar/projetos/" + projetoId;
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gateway-Secret", gatewaySecret);
            
            // Propagar headers do request original se disponíveis
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String authorization = request.getHeader("Authorization");
                    String userId = request.getHeader("X-User-Id");
                    String userEmail = request.getHeader("X-User-Email");
                    
                    if (authorization != null) {
                        headers.set("Authorization", authorization);
                    }
                    if (userId != null) {
                        headers.set("X-User-Id", userId);
                    }
                    if (userEmail != null) {
                        headers.set("X-User-Email", userEmail);
                    }
                }
            } catch (Exception e) {
                // Ignorar se não houver contexto de request (ex: testes)
            }
            
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<ProjetoDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProjetoDTO.class);
            
            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado: " + e.getMessage());
        }
    }
}
