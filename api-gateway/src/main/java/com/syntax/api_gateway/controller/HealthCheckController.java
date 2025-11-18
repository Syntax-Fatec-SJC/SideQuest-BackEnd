package com.syntax.api_gateway.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.syntax.api_gateway.configuracao.PropriedadesMicroservicos;

import reactor.core.publisher.Mono;

/**
 * Controller para health check do API Gateway e microserviços
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Autowired
    private PropriedadesMicroservicos propriedades;

    @Autowired
    private WebClient webClient;

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "API Gateway");
        health.put("timestamp", LocalDateTime.now());
        health.put("message", "API Gateway está funcionando corretamente");
        
        return ResponseEntity.ok(health);
    }

    @GetMapping("/services")
    public ResponseEntity<Map<String, Object>> checkServices() {
        Map<String, Object> servicesStatus = new HashMap<>();
        servicesStatus.put("timestamp", LocalDateTime.now());
        
        // Check Usuario Service
        Mono<String> usuarioStatus = checkServiceHealth(propriedades.getUsuario().getUrl());
        
        // Check Projetos Service
        Mono<String> projetosStatus = checkServiceHealth(propriedades.getProjetos().getUrl());
        
        // Check Tarefas Service
        Mono<String> tarefasStatus = checkServiceHealth(propriedades.getTarefas().getUrl());

        return Mono.zip(usuarioStatus, projetosStatus, tarefasStatus)
            .map(tuple -> {
                Map<String, Object> services = new HashMap<>();
                services.put("usuario-service", Map.of(
                    "url", propriedades.getUsuario().getUrl(),
                    "status", tuple.getT1()
                ));
                services.put("projetos-service", Map.of(
                    "url", propriedades.getProjetos().getUrl(),
                    "status", tuple.getT2()
                ));
                services.put("tarefas-service", Map.of(
                    "url", propriedades.getTarefas().getUrl(),
                    "status", tuple.getT3()
                ));
                
                servicesStatus.put("services", services);
                servicesStatus.put("gateway-status", "UP");
                
                return ResponseEntity.ok(servicesStatus);
            })
            .onErrorResume(e -> {
                servicesStatus.put("gateway-status", "UP");
                servicesStatus.put("error", "Erro ao verificar serviços: " + e.getMessage());
                return Mono.just(ResponseEntity.status(503).body(servicesStatus));
            })
            .block();
    }

    private Mono<String> checkServiceHealth(String baseUrl) {
        return webClient.get()
            .uri(baseUrl + "/actuator/health")
            .retrieve()
            .bodyToMono(String.class)
            .map(response -> "UP")
            .onErrorReturn("DOWN");
    }
}
