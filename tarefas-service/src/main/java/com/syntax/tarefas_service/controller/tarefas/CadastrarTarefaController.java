package com.syntax.tarefas_service.controller.tarefas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.tarefas_service.modelo.dto.tarefaDTO.TarefaDTO;
import com.syntax.tarefas_service.service.tarefas.CadastrarTarefaService;

import jakarta.validation.Valid;

/**
 * Controller para cadastrar nova tarefa
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class CadastrarTarefaController {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarTarefaController.class);

    @Autowired
    private CadastrarTarefaService cadastrarTarefaService;

    @PostMapping("/cadastrar/tarefas")
    public ResponseEntity<TarefaDTO> cadastrar(
            @Valid @RequestBody TarefaDTO dto,
            jakarta.servlet.http.HttpServletRequest request) {
        
        String autorId = (String) request.getAttribute("userId");
        String autorNome = (String) request.getAttribute("userName");
        
        logger.info("🎯 CADASTRAR TAREFA - autorId: {}, autorNome: {}, descricao: {}", autorId, autorNome, dto.getDescricao());
        
        TarefaDTO criada = cadastrarTarefaService.executar(dto, autorId, autorNome);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }
}
