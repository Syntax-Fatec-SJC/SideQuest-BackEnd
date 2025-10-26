package com.syntax.sidequest_backend.controller.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.sidequest_backend.service.projetos.AtualizarProjetoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("atualizarProjetoController")
public class AtualizarProjetoController {

    @Autowired
    private AtualizarProjetoService atualizarProjetoService;

    @PutMapping("/atualizar/projetos/{id}")
    public ResponseEntity<ProjetoDTO> atualizar(@PathVariable String id, @Valid @RequestBody ProjetoDTO dto) {
        ProjetoDTO atualizado = atualizarProjetoService.executar(id, dto);
        return ResponseEntity.ok(atualizado);
    }
}
