package com.syntax.projetos_service.controller.projetos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.CadastrarProjetoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Controller para cadastrar projetos Suporta ambas as rotas (antiga e nova)
 * para compatibilidade
 */
@RestController
public class CadastrarProjetoController {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarProjetoController.class);

    @Autowired
    private CadastrarProjetoService service;

    /**
     * Rota ANTIGA (mantida para compatibilidade) POST /cadastrar/projetos
     */
    @PostMapping("/cadastrar/projetos")
    public ResponseEntity<ProjetoDTO> cadastrar(
            @Valid @RequestBody ProjetoDTO dto,
            HttpServletRequest request) {

        String usuarioId = (String) request.getAttribute("userId");
        String userEmail = (String) request.getAttribute("userEmail");

        logger.info("Cadastrando projeto para usuário: {} ({})", userEmail, usuarioId);

        ProjetoDTO resultado = service.executar(dto, usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    /**
     * Rota NOVA (usada pelo frontend atualizado) POST /projetos
     */
    @PostMapping("/projetos")
    public ResponseEntity<ProjetoDTO> cadastrarProjeto(
            @Valid @RequestBody ProjetoDTO dto,
            HttpServletRequest request) {

        String usuarioId = (String) request.getAttribute("userId");
        String userEmail = (String) request.getAttribute("userEmail");

        logger.info("Cadastrando projeto (rota nova) para usuário: {} ({})", userEmail, usuarioId);

        ProjetoDTO resultado = service.executar(dto, usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
