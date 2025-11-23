package com.syntax.projetos_service.controller.projetos.membroProjeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.MembroProjetoDTO;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.membroProjeto.AdicionarMembroProjetoService;

import jakarta.validation.Valid;

/**
 * Controller para adicionar membros ao projeto
 */
@RestController
public class AdicionarMembroProjetoController {

    @Autowired
    private AdicionarMembroProjetoService service;

    @PostMapping("/projetos/{projetoId}/membros")
    public ResponseEntity<ProjetoDTO> adicionarMembro(
            @PathVariable String projetoId,
            @Valid @RequestBody MembroProjetoDTO dto,
            jakarta.servlet.http.HttpServletRequest request) {
        
        String autorId = (String) request.getAttribute("userId");
        String autorNome = (String) request.getAttribute("userName");
        
        ProjetoDTO resultado = service.executar(projetoId, dto, autorId, autorNome);
        return ResponseEntity.ok(resultado);
    }
}
