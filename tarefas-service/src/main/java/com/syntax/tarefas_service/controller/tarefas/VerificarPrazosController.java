package com.syntax.tarefas_service.controller.tarefas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.scheduler.VerificadorPrazoTarefasScheduler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller para verificação manual de prazos de tarefas
 */
@RestController
@RequestMapping
@Tag(name = "Tarefas", description = "Endpoints relacionados a tarefas")
public class VerificarPrazosController {

    private final VerificadorPrazoTarefasScheduler verificadorPrazoTarefasScheduler;

    public VerificarPrazosController(VerificadorPrazoTarefasScheduler verificadorPrazoTarefasScheduler) {
        this.verificadorPrazoTarefasScheduler = verificadorPrazoTarefasScheduler;
    }

    /**
     * Endpoint para executar manualmente a verificação de prazos
     * Útil para testes sem esperar o agendamento automático (8h da manhã)
     */
    @PostMapping("/verificar-prazos")
    @Operation(summary = "Verificar prazos manualmente", 
               description = "Executa a verificação de prazos de tarefas e cria avisos para tarefas que vencem hoje ou já venceram. Útil para testes.")
    public ResponseEntity<String> verificarPrazos() {
        verificadorPrazoTarefasScheduler.verificarPrazosTarefas();
        return ResponseEntity.ok("Verificação de prazos executada com sucesso!");
    }
}
