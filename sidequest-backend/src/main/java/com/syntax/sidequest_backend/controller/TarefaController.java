package com.syntax.sidequest_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.service.AnexoService;
import com.syntax.sidequest_backend.service.TarefaService;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
public class TarefaController {

    @Autowired
    private TarefaService service;

    @Autowired
    private AnexoService anexoService;

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

    // Criar tarefa com JSON puro (SEM arquivos)
    @PostMapping("/cadastrar/tarefas")
    public ResponseEntity<Tarefa> criar(@RequestBody TarefaDTO tarefaDto) {
        try {
            Tarefa tarefaCriada = service.criarTarefa(tarefaDto);
            return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ ANTIGO: Criar tarefa com arquivos (multipart) - MANTIDO para compatibilidade
    @PostMapping("/cadastrar/tarefas/com-arquivos")
    public ResponseEntity<Tarefa> criarComArquivos(
            @RequestPart("tarefa") String tarefaJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            TarefaDTO tarefaDto = mapper.readValue(tarefaJson, TarefaDTO.class);
            Tarefa tarefaCriada = service.criarTarefa(tarefaDto);

            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    anexoService.salvarAnexo(file, tarefaCriada.getId());
                }
            }

            return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ NOVO: Atualizar tarefa com JSON puro (SEM arquivos)
    @PutMapping("/atualizar/tarefas/{id}")
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable String id,
            @RequestBody TarefaDTO tarefaDto) {
        try {
            tarefaDto.setId(id);
            Tarefa tarefaAtualizada = service.atualizarTarefa(tarefaDto);
            return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ ANTIGO: Atualizar tarefa com arquivos (multipart) - MANTIDO
    @PutMapping("/atualizar/tarefas/{id}/com-arquivos")
    public ResponseEntity<Tarefa> atualizarComArquivos(
            @PathVariable String id,
            @RequestPart("tarefa") String tarefaJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            TarefaDTO tarefaDto = mapper.readValue(tarefaJson, TarefaDTO.class);
            tarefaDto.setId(id);
            Tarefa tarefaAtualizada = service.atualizarTarefa(tarefaDto);

            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    anexoService.salvarAnexo(file, id);
                }
            }

            return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/excluir/tarefas/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        // Deleta os anexos primeiro
        anexoService.deletarPorTarefa(id);
        // Depois deleta a tarefa
        service.excluirTarefaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projetos/{projetoId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorProjeto(@PathVariable String projetoId) {
        return ResponseEntity.ok(service.listarPorProjeto(projetoId));
    }

    @GetMapping("/listar/{projetoId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorProjetoPadrao(@PathVariable String projetoId) {
        return ResponseEntity.ok(service.listarPorProjeto(projetoId));
    }

    @GetMapping("/usuarios/{usuarioId}/tarefas")
    public ResponseEntity<List<Tarefa>> listarPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PatchMapping("/tarefas/{id}/responsaveis")
    public ResponseEntity<Tarefa> atualizarResponsaveis(@PathVariable String id, @RequestBody AtualizarResponsaveisDTO dto) {
        Tarefa t = service.atualizarResponsaveis(id, dto.getUsuarioIds());
        return ResponseEntity.ok(t);
    }
}

class AtualizarResponsaveisDTO {

    private java.util.List<String> usuarioIds;

    public java.util.List<String> getUsuarioIds() {
        return usuarioIds;
    }

    public void setUsuarioIds(java.util.List<String> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }
}
