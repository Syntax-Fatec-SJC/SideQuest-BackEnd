package com.syntax.sidequest_backend.controller.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.sidequest_backend.service.projetos.DeletarProjetoService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("deletarProjetoController")
public class DeletarProjetoController {

    @Autowired
    private DeletarProjetoService deletarProjetoService;

    @DeleteMapping("/excluir/projetos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        deletarProjetoService.executar(id);
        return ResponseEntity.noContent().build();
    }
}
