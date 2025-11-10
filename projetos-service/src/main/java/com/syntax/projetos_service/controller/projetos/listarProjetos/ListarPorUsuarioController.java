package com.syntax.projetos_service.controller.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.service.projetos.listarProjetos.ListarPorUsuarioService;

/**
 * Controller para listar projetos por usuário
 */
@RestController
public class ListarPorUsuarioController {

    @Autowired
    private ListarPorUsuarioService service;

    @GetMapping("/listar/projetos/meus")
    public ResponseEntity<List<ProjetoDTO>> listarPorUsuario(
            @RequestHeader("X-User-Id") String usuarioId) {
        List<ProjetoDTO> projetos = service.executar(usuarioId);
        return ResponseEntity.ok(projetos);
    }
}
