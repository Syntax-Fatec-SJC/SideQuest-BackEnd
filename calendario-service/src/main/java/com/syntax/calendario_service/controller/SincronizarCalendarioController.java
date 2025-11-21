package com.syntax.calendario_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.calendario_service.service.google.SincronizarTarefasService;

@RestController
@RequestMapping("/api/calendar")
public class SincronizarCalendarioController {
    
    @Autowired
    private SincronizarTarefasService sincronizarTarefasService;

    @PostMapping("/sync")
    public ResponseEntity<Void> execute(@RequestAttribute("userId") Long usuarioId) {
        sincronizarTarefasService.execute(usuarioId);
        return ResponseEntity.ok().build();
    }
}
