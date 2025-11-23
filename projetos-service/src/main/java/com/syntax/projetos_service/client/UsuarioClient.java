package com.syntax.projetos_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.dto.usuarioDTO.UsuarioDTO;

/**
 * Client para comunicação com Usuario-Service
 */
@Component
public class UsuarioClient {

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;
    
    @Value("${gateway.secret:SideQuestGatewaySecret2024}")
    private String gatewaySecret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Verifica se um usuário existe
     */
    public boolean usuarioExiste(String usuarioId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gateway-Secret", gatewaySecret);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            String url = usuarioServiceUrl + "/usuarios/" + usuarioId;
            restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, 
                "Erro ao comunicar com Usuario-Service: " + e.getMessage());
        }
    }

    /**
     * Verifica se múltiplos usuários existem
     */
    public boolean todosUsuariosExistem(java.util.List<String> usuarioIds) {
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            return true;
        }
        
        for (String usuarioId : usuarioIds) {
            if (!usuarioExiste(usuarioId)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Busca informações detalhadas de um usuário via endpoint interno
     */
    public UsuarioDTO buscarUsuario(String usuarioId) {
        try {
            String url = usuarioServiceUrl + "/internal/usuarios/" + usuarioId;
            
            // Adiciona header de segurança para comunicação entre serviços
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-Gateway-Secret", "SideQuestGatewaySecret2024");
            
            org.springframework.http.HttpEntity<Void> entity = new org.springframework.http.HttpEntity<>(headers);
            
            org.springframework.http.ResponseEntity<UsuarioDTO> response = 
                restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, UsuarioDTO.class);
            
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, 
                "Erro ao comunicar com Usuario-Service: " + e.getMessage());
        }
    }
}
