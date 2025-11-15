package com.syntax.projetos_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetosServiceApplication.class, args);
		System.out.println("Projetos Service iniciado na porta 8083");
	}

}
