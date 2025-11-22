package com.syntax.tarefas_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Cliente para comunicação com o Usuario Service
 */
@Component
public class UsuariosClient {

    private static final Logger logger = LoggerFactory.getLogger(UsuariosClient.class);

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    @Value("${gateway.secret}")
    private String gatewaySecret;

    private final RestTemplate restTemplate;

    public UsuariosClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca o nome de um usuário pelo ID
     */
    public String buscarNomeUsuario(String usuarioId) {
        try {
            String url = usuarioServiceUrl + "/listar/usuarios/" + usuarioId;
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gateway-Secret", gatewaySecret);
            
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                request, 
                JsonNode.class
            );
            
            JsonNode body = response.getBody();
            if (body != null && body.has("nome")) {
                return body.get("nome").asText();
            }
            
            logger.warn("⚠️ Nome não encontrado para usuário: {}", usuarioId);
            return null;
        } catch (RestClientException e) {
            logger.warn("⚠️ Falha ao buscar nome do usuário {}: {}", usuarioId, e.getMessage());
            return null;
        }
    }
}
