package com.syntax.projetos_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * Client para comunicação com Usuario-Service
 */
@Component
public class UsuarioClient {

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Verifica se um usuário existe
     */
    public boolean usuarioExiste(String usuarioId) {
        try {
            String url = usuarioServiceUrl + "/listar/usuarios/" + usuarioId;
            restTemplate.getForObject(url, Object.class);
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
}
