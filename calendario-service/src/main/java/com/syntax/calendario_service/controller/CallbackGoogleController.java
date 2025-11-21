package com.syntax.calendario_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.calendario_service.service.google.ObterTokenAcessoService;

@RestController
@RequestMapping("/api/calendar")
public class CallbackGoogleController {
    
    @Autowired
    private ObterTokenAcessoService obterTokenAcessoService;

    @GetMapping("/oauth2/callback")
    public ResponseEntity<String> execute(@RequestParam("code") String code, @RequestParam("state") String state) {
        Long usuarioID= Long.parseLong(state);
        obterTokenAcessoService.execute(code, usuarioID);
        return ResponseEntity.ok("<script>window.close();</script>");
    }
}
