package com.syntax.projetos_service.controller.projetos.membroProjeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.membroProjeto.RemoverMembroProjetoService;

/**
 * Controller para remover membro do projeto
 */
@RestController
public class RemoverMembroProjetoController {

    @Autowired
    private RemoverMembroProjetoService service;

    @DeleteMapping("/projetos/{projetoId}/membros/{usuarioId}")
    public ResponseEntity<ProjetoDTO> removerMembro(
            @PathVariable String projetoId,
            @PathVariable String usuarioId) {
        
        ProjetoDTO resultado = service.executar(projetoId, usuarioId);
        return ResponseEntity.ok(resultado);
    }
}
