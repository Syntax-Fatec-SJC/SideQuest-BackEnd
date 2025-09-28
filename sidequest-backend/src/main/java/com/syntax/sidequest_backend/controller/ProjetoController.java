package com.syntax.sidequest_backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.MembroProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.service.ProjetoService;

import jakarta.validation.Valid;

@RestController
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @PostMapping("/cadastrar/projetos")
    public ResponseEntity<Projeto> criar(
            @RequestParam("usuarioIdCriador") String usuarioIdCriador,
            @RequestBody @Valid ProjetoDTO projetoDto) {

        Projeto projetoCriado = service.criarProjeto(projetoDto, usuarioIdCriador);
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoCriado);
    }

    @GetMapping("/listar/projetos")
    public ResponseEntity<List<Projeto>> listar(){
        List<Projeto> projetos = service.listarProjetos();
        ResponseEntity<List<Projeto>> resposta = new ResponseEntity<>(projetos, HttpStatus.OK);
        return resposta;
    }

    @GetMapping("/listar/{usuarioId}/projetos")
    public ResponseEntity<List<Projeto>> listarPorUsuario(@PathVariable String usuarioId){
        List<Projeto> projetos = service.listarProjetosPorUsuario(usuarioId);
        return ResponseEntity.ok(projetos);
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
        service.excluirProjetoPorId(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping("/listar/{projetoId}/membros")
    public ResponseEntity<List<MembroProjetoDTO>> listarMembros(@PathVariable String projetoId) {
        List<MembroProjetoDTO> membros = service.listarMembros(projetoId);
        return ResponseEntity.ok(membros);
    }

    @PostMapping("/adicionar/{projetoId}/membros/{usuarioId}")
    public ResponseEntity<Void> adicionarMembro(@PathVariable String projetoId, @PathVariable String usuarioId) {
        service.adicionarMembro(projetoId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/excluir/{projetoId}/membros/{usuarioId}")
    public ResponseEntity<Void> removerMembro(@PathVariable String projetoId, @PathVariable String usuarioId) {
        service.removerMembro(projetoId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
