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
 * Controller para cadastrar projetos
 * Usa userId propagado pelo API Gateway via headers
 */
@RestController
public class CadastrarProjetoController {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarProjetoController.class);

    @Autowired
    private CadastrarProjetoService service;

    @PostMapping("/cadastrar/projetos")
    public ResponseEntity<ProjetoDTO> cadastrar(
            @Valid @RequestBody ProjetoDTO dto,
            HttpServletRequest request) {
        
        // Extrai userId e userName do header propagado pelo Gateway (validado no filtro)
        String usuarioId = (String) request.getAttribute("userId");
        String userEmail = (String) request.getAttribute("userEmail");
        String userName = (String) request.getAttribute("userName");
        
        logger.info("📝 Cadastrando projeto para usuário: {} ({}) - Nome: {}", userEmail, usuarioId, userName);
        
        ProjetoDTO resultado = service.executar(dto, usuarioId, userName);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
