package com.syntax.projetos_service.controller.projetos;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.projetos_service.service.projetos.DeletarProjetoService;
import com.syntax.projetos_service.service.projetos.RestaurarProjetoService;

/**
 * Controller para integração de projetos com a lixeira
 */
@RestController
@RequestMapping("/projetos")
public class DeletarProjetoController {

    @Autowired
    private DeletarProjetoService deletarService;

    @Autowired
    private RestaurarProjetoService restaurarService;

    /**
     * Deleta um projeto e envia para a lixeira
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        deletarService.executar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta permanentemente (chamado pela lixeira)
     */
    @DeleteMapping("/deletar-permanente/{id}")
    public ResponseEntity<Void> deletarPermanente(@PathVariable String id) {
        deletarService.deletarPermanentemente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Restaura um projeto da lixeira
     */
    @PostMapping("/restaurar")
    public ResponseEntity<Map<String, String>> restaurar(@RequestBody Map<String, Object> dados) {
        restaurarService.restaurar(dados);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Projeto restaurado com sucesso",
                "id", dados.get("id").toString()
        ));
    }
}
