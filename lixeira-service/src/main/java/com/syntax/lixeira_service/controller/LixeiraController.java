package com.syntax.lixeira_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.syntax.lixeira_service.modelo.ItemLixeiraDTO;
import com.syntax.lixeira_service.modelo.RecebimentoLixeiraDTO;
import com.syntax.lixeira_service.service.LixeiraService;

@RestController
@RequestMapping("/api/lixeira")
public class LixeiraController {

    @Autowired
    private LixeiraService lixeiraService;

    // Receber item deletado (tarefas ou projetos)
    @PostMapping("/criar")
    public ResponseEntity<ItemLixeiraDTO> receberItem(@RequestBody RecebimentoLixeiraDTO recebimento) {
        ItemLixeiraDTO item = lixeiraService.receberItemDeletado(recebimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    // Listar todos os itens
    @GetMapping("/listar")
    public ResponseEntity<List<ItemLixeiraDTO>> listarTodos() {
        return ResponseEntity.ok(lixeiraService.listarTodos());
    }

    @GetMapping("/listar/tarefas")
    public ResponseEntity<List<ItemLixeiraDTO>> listarTarefas() {
        return ResponseEntity.ok(lixeiraService.listarTarefas());
    }

    @GetMapping("/listar/projetos")
    public ResponseEntity<List<ItemLixeiraDTO>> listarProjetos() {
        return ResponseEntity.ok(lixeiraService.listarProjetos());
    }

    @GetMapping("/listar/usuario/{userId}")
    public ResponseEntity<List<ItemLixeiraDTO>> listarPorUsuario(@PathVariable String userId) {
        return ResponseEntity.ok(lixeiraService.listarPorUsuario(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemLixeiraDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(lixeiraService.buscarPorId(id));
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarItens() {
        return ResponseEntity.ok(lixeiraService.contarItens());
    }

    // Restauração
    @PutMapping("/restaurar/{id}")
    public ResponseEntity<ItemLixeiraDTO> restaurar(@PathVariable String id) {
        ItemLixeiraDTO item = lixeiraService.restaurar(id);
        return ResponseEntity.ok(item);
    }

    // Deletar permanentemente
    @DeleteMapping("/{id}/deletar-permanente")
    public ResponseEntity<Void> deletarPermanentemente(@PathVariable String id) {
        lixeiraService.deletarPermanentemente(id);
        return ResponseEntity.noContent().build();
    }

    // Esvaziar lixeira
    @DeleteMapping("/esvaziar/tarefas")
    public ResponseEntity<Void> esvaziarTarefas() {
        lixeiraService.esvaziarTarefas();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/esvaziar/projetos")
    public ResponseEntity<Void> esvaziarProjetos() {
        lixeiraService.esvaziarProjetos();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/esvaziar/tudo")
    public ResponseEntity<Void> esvaziarTudo() {
        lixeiraService.esvaziarTudo();
        return ResponseEntity.noContent().build();
    }

    // Limpeza imediata
    @PostMapping("/limpar-agora")
    public ResponseEntity<String> limparAgora() {
        int deletados = lixeiraService.limparAgora();
        return ResponseEntity.ok(deletados + " itens deletados automaticamente");
    }

    // Estatísticas
    @GetMapping("/estatisticas")
    public ResponseEntity<String> obterEstatisticas() {
        return ResponseEntity.ok(lixeiraService.obterEstatisticas());
    }
}
