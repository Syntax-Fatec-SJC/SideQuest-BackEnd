package com.syntax.sidequest_backend.controller.tarefas.listarTarefas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.dto.tarefasDTO.TarefaDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;
import com.syntax.sidequest_backend.service.tarefas.listarTarefas.ListarPorUsuarioService;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController("listarTarefasPorUsuarioController")
public class ListarPorUsuarioController {

    @Autowired
    private ListarPorUsuarioService listarPorUsuarioService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/tarefas/minhas")
    public ResponseEntity<List<TarefaDTO>> listarTarefasDoUsuarioAutenticado(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String email = userDetails.getUsername();

        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        String usuarioId = usuarioOpt.get().getId().toString();

        return ResponseEntity.ok(listarPorUsuarioService.executar(usuarioId));
    }
}
