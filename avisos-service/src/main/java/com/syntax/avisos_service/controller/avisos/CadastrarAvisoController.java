package com.syntax.avisos_service.controller.avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.avisos_service.modelo.dto.avisoDTO.AvisoDTO;
import com.syntax.avisos_service.service.avisos.CadastrarAvisoService;

import jakarta.validation.Valid;

/**
 * Controller para cadastrar novo aviso
 */
@RestController
public class CadastrarAvisoController {

    @Autowired
    private CadastrarAvisoService cadastrarAvisoService;

    @PostMapping("/cadastrar/avisos")
    public ResponseEntity<AvisoDTO> cadastrar(@Valid @RequestBody AvisoDTO dto) {
        AvisoDTO criado = cadastrarAvisoService.executar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
}
