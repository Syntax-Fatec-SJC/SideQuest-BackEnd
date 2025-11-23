package com.syntax.avisos_service.controller.avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.service.avisos.MarcarComoVisualizadoService;

/**
 * Controller para marcar aviso como visualizado
 */
@RestController
public class MarcarComoVisualizadoController {

    @Autowired
    private MarcarComoVisualizadoService marcarComoVisualizadoService;

    @PatchMapping("/avisos/{avisoId}/visualizado")
    public ResponseEntity<AvisoDTO> marcarComoVisualizado(@PathVariable String avisoId) {
        AvisoDTO atualizado = marcarComoVisualizadoService.executar(avisoId);
        return ResponseEntity.ok(atualizado);
    }
}
