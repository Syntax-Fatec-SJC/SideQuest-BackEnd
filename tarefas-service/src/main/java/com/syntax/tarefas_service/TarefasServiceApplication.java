package com.syntax.tarefas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TAREFAS SERVICE - MICROSERVIÇO DE GERENCIAMENTO DE TAREFAS
 * 
 * Responsabilidades:
 * - CRUD de tarefas
 * - Gerenciar status (Pendente, Desenvolvimento, Concluído)
 * - Gerenciar prazos e anexos
 * - Associar tarefas a projetos e usuários
 * - Verificar automaticamente prazos e criar avisos
 * 
 * Porta: 8084
 * Database: syntaxbd (collection: tarefas)
 */
@SpringBootApplication
@EnableScheduling
public class TarefasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarefasServiceApplication.class, args);
		System.out.println("✅ Tarefas Service iniciado na porta 8084");
	}

}
