package com.syntax.lixeira_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Cliente para comunicação com projetos-service através do API Gateway
 */
@Component
public class ProjetosClient {

    @Value("${api.gateway.url:http://localhost:8080}")
    private String apiGatewayUrl;

    @Value("${gateway.secret:SideQuestGatewaySecret2024}")
    private String gatewaySecret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Restaura um projeto chamando o endpoint /restaurar/projetos do
     * projetos-service
     *
     * @param dados Dados completos do projeto a ser restaurado
     */
    public void restaurarProjeto(Map<String, Object> dados) {
        try {
            String url = apiGatewayUrl + "/api/projetos/restaurar/projetos";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Gateway-Secret", gatewaySecret);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(dados, headers);

            System.out.println("📡 POST " + url);
            restTemplate.postForObject(url, request, String.class);
            System.out.println("✅ Projeto restaurado via Gateway");

        } catch (Exception e) {
            System.err.println("❌ Erro ao restaurar projeto: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao restaurar projeto", e);
        }
    }

    /**
     * Deleta permanentemente um projeto
     *
     * @param projetoId ID do projeto a ser deletado
     */
    public void deletarPermanente(String projetoId) {
        try {
            String url = apiGatewayUrl + "/api/projetos/deletar-permanente/projetos/" + projetoId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gateway-Secret", gatewaySecret);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            System.out.println("📡 DELETE " + url);
            restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            System.out.println("✅ Projeto deletado permanentemente via Gateway");

        } catch (Exception e) {
            System.err.println("⚠️ Erro ao deletar permanentemente (pode já estar deletado): " + e.getMessage());
            // Não propaga a exceção - é esperado que o item possa já não existir
        }
    }
}
