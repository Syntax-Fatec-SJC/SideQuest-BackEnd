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
 * Cliente para comunicação com tarefas-service através do API Gateway
 */
@Component
public class TarefasClient {

    @Value("${api.gateway.url:http://localhost:8080}")
    private String apiGatewayUrl;

    @Value("${gateway.secret:SideQuestGatewaySecret2024}")
    private String gatewaySecret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Restaura uma tarefa chamando o endpoint /restaurar/tarefas do
     * tarefas-service
     *
     * @param dados Dados completos da tarefa a ser restaurada
     */
    public void restaurarTarefa(Map<String, Object> dados) {
        try {
            String url = apiGatewayUrl + "/api/tarefas/restaurar/tarefas";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Gateway-Secret", gatewaySecret);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(dados, headers);

            System.out.println("📡 POST " + url);
            restTemplate.postForObject(url, request, String.class);
            System.out.println("✅ Tarefa restaurada via Gateway");

        } catch (Exception e) {
            System.err.println("❌ Erro ao restaurar tarefa: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao restaurar tarefa", e);
        }
    }

    /**
     * Deleta permanentemente uma tarefa
     *
     * @param tarefaId ID da tarefa a ser deletada
     */
    public void deletarPermanente(String tarefaId) {
        try {
            String url = apiGatewayUrl + "/api/tarefas/deletar-permanente/tarefas/" + tarefaId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gateway-Secret", gatewaySecret);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            System.out.println("📡 DELETE " + url);
            restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            System.out.println("✅ Tarefa deletada permanentemente via Gateway");

        } catch (Exception e) {
            System.err.println("⚠️ Erro ao deletar permanentemente (pode já estar deletada): " + e.getMessage());
            // Não propaga a exceção - é esperado que o item possa já não existir
        }
    }
}
