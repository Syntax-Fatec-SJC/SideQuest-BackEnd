package com.syntax.sidequest_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.syntax.sidequest_backend.modelo.dto.MembroDTO;
import com.syntax.sidequest_backend.modelo.entidade.Membro;
import com.syntax.sidequest_backend.service.MembroService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/membros")
public class MembroController {

    @Autowired
    private MembroService service;

    @PostMapping
    public Membro adicionar(@RequestBody @Valid MembroDTO dto) {
        return service.adicionarMembro(dto);
    }

    @GetMapping
    public List<Membro> listar() {
        return service.listarMembros();
    }

    @PutMapping("/{membroId}")
    public Membro atualizar(@PathVariable String membroId, @RequestBody @Valid MembroDTO dto) {
        return service.atualizarMembro(membroId, dto);
    }

    @DeleteMapping("/{membroId}")
    public void remover(@PathVariable String membroId) {
        service.removerMembro(membroId);
    }
}
