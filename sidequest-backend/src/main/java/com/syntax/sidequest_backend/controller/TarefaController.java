package com.syntax.sidequest_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.AtualizarResponsaveisDTO;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.service.interfaces.ITarefaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
public class TarefaController {

    private final ITarefaService service;
    
    public TarefaController(ITarefaService service) {
        this.service = service;
    }

    @GetMapping("/listar/tarefas")
    public ResponseEntity<List<Tarefa>> listar() {
        List<Tarefa> tarefas = service.listarTodos();
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping("/cadastrar/tarefas")
    public ResponseEntity<Tarefa> criar(@RequestBody @Valid TarefaDTO tarefaDto) {
        Tarefa tarefaCriada = service.criar(tarefaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    @PutMapping("/atualizar/tarefas/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable String id, @RequestBody @Valid TarefaDTO tarefaDto) {
        tarefaDto.setId(id);
        Tarefa tarefaAtualizada = service.atualizar(tarefaDto);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @DeleteMapping("/excluir/tarefas/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projetos/{projetoId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorProjeto(@PathVariable String projetoId) {
        return ResponseEntity.ok(service.listarPorProjeto(projetoId));
    }

    @GetMapping("/usuarios/{usuarioId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PatchMapping("/tarefas/{id}/responsaveis")
    public ResponseEntity<Tarefa> atualizarResponsaveis(
            @PathVariable String id, 
            @RequestBody @Valid AtualizarResponsaveisDTO dto) {
        Tarefa tarefa = service.atualizarResponsaveis(id, dto.getUsuarioIds());
        return ResponseEntity.ok(tarefa);
    }
}
