package com.syntax.calendario_service.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.calendario_service.service.google.GerarUrlAutorizacaoService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/calendar")
public class AutorizarGoogleController {
    
    @Autowired
    private GerarUrlAutorizacaoService gerarUrlAutorizacaoService;

    @GetMapping("/oauth2/authorize")
    public void execute(@RequestParam("state") String usuarioId, HttpServletResponse response) throws IOException {
        String url = gerarUrlAutorizacaoService.execute(usuarioId);
        response.sendRedirect(url);
    }
}
