package com.syntax.sidequest_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.service.ProjetoService;

import jakarta.validation.Valid;

@RestController
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @GetMapping("/listar/projetos")
    public List<Projeto> listar() {
        return service.listarProjetos();
    }

    @PostMapping("/cadastrar/projetos")
    public ResponseEntity<Projeto> criar(@RequestBody @Valid ProjetoDTO projetoDto) {
        Projeto projetoCriado = service.criarProjeto(projetoDto);
        ResponseEntity<Projeto> resposta = new ResponseEntity<>(projetoCriado, HttpStatus.OK);
        return resposta;
    }

    @PutMapping("/atualizar/projetos/{id}")
    public ResponseEntity<Projeto> atualizar(@PathVariable String id, @RequestBody @Valid ProjetoDTO projetoDto) {
        projetoDto.setId(id); 
        Projeto projetoAtualizado = service.atualizarProjeto(projetoDto);
        ResponseEntity<Projeto> resposta = new ResponseEntity<>(projetoAtualizado, HttpStatus.OK);
        return resposta;
    }

    @DeleteMapping("/excluir/projetos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluirProjeto(id);
        return ResponseEntity.noContent().build();
    }
}
