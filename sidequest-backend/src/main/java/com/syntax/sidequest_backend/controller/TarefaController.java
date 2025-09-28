package com.syntax.sidequest_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.service.TarefaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
public class TarefaController {

    @Autowired
    private TarefaService service;

    @GetMapping("/listar/tarefas")
    public ResponseEntity<List<Tarefa>> listar() {
        List<Tarefa> tarefas = service.listarTarefas();
        ResponseEntity<List<Tarefa>> resposta = new ResponseEntity<>(tarefas, HttpStatus.OK);
        return resposta;
    }

    @GetMapping("/buscar/tarefas/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable String id) {
        Tarefa tarefa = service.buscarTarefa(id);
        ResponseEntity<Tarefa> resposta = new ResponseEntity<>(tarefa, HttpStatus.OK);
        return resposta;
    }

    @PostMapping("/cadastrar/tarefas")
    public ResponseEntity<Tarefa> criar(@RequestBody @Valid TarefaDTO tarefaDto) {
        Tarefa tarefaCriada = service.criarTarefa(tarefaDto);
        ResponseEntity<Tarefa> resposta = new ResponseEntity<>(tarefaCriada, HttpStatus.OK);
        return resposta;
    }

    @PutMapping("/atualizar/tarefas/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable String id, @RequestBody @Valid TarefaDTO tarefaDto) {
        tarefaDto.setId(id);
        Tarefa tarefaAtualizada = service.atualizarTarefa(tarefaDto);
        ResponseEntity<Tarefa> resposta = new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        return resposta;
    }

    @DeleteMapping("/excluir/tarefas/{id}")
    public void excluir(@PathVariable String id, @RequestBody TarefaDTO tarefaDto) {
        tarefaDto.setId(id);
        service.excluirTarefa(tarefaDto);
    }

    @GetMapping("/projetos/{projetoId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorProjeto(@PathVariable String projetoId) {
        return ResponseEntity.ok(service.listarPorProjeto(projetoId));
    }

    @GetMapping("/usuarios/{usuarioId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }
}
