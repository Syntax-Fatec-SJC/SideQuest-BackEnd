package com.syntax.projetos_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.syntax.projetos_service.modelo.dto.projetoDTO.RecebimentoLixeiraDTO;

/**
 * Cliente para comunicação com o serviço de lixeira
 */
@Component
public class LixeiraClient {

    @Value("${lixeira.service.url:http://localhost:8085}")
    private String lixeiraServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Envia um item para a lixeira
     *
     * @param lixeira Dados do item a ser enviado
     */
    public void enviarParaLixeira(RecebimentoLixeiraDTO lixeira) {
        try {
            String url = lixeiraServiceUrl + "/api/lixeira/criar";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Gateway-Secret", "SideQuestGatewaySecret2024");

            HttpEntity<RecebimentoLixeiraDTO> request = new HttpEntity<>(lixeira, headers);

            restTemplate.postForObject(url, request, String.class);
            System.out.println(" Item enviado para lixeira com sucesso: " + lixeira.getItemId());
        } catch (RuntimeException e) {
            System.err.println(" Erro ao enviar para lixeira: " + e.getMessage());
            e.printStackTrace();
            throw e; // Propaga a exceção para tratamento no service
        }
    }
}
