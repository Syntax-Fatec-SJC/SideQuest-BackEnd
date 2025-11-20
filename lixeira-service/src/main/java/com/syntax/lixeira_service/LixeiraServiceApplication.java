package com.syntax.lixeira_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LixeiraServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LixeiraServiceApplication.class, args);
    }
}
