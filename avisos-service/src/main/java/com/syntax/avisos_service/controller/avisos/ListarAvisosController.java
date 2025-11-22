package com.syntax.avisos_service.controller.avisos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.service.avisos.ListarAvisosService;

/**
 * Controller para listar avisos
 */
@RestController
public class ListarAvisosController {

    @Autowired
    private ListarAvisosService listarAvisosService;

    /**
     * Lista todos os avisos de um usuário
     */
    @GetMapping("/avisos/usuario/{usuarioId}")
    public ResponseEntity<List<AvisoDTO>> listarPorUsuario(
            @PathVariable String usuarioId,
            @RequestParam(required = false, defaultValue = "false") Boolean apenasNaoVisualizados) {
        
        List<AvisoDTO> avisos;
        
        if (apenasNaoVisualizados) {
            avisos = listarAvisosService.listarNaoVisualizados(usuarioId);
        } else {
            avisos = listarAvisosService.listarTodos(usuarioId);
        }
        
        return ResponseEntity.ok(avisos);
    }
}
