package com.syntax.sidequest_backend.service.projetos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.sidequest_backend.modelo.dto.projetoDTO.MembroProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Service
public class MembroProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<MembroProjetoDTO> listarMembros(String projetoId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        if (projeto.getUsuarioIds() == null || projeto.getUsuarioIds().isEmpty()) {
            return List.of();
        }

        String criadorId = projeto.getUsuarioIds().get(0);

        return projeto.getUsuarioIds().stream()
                .map(usuarioId -> {
                    Optional<Usuario> opt = usuarioRepositorio.findById(usuarioId);
                    if (opt.isEmpty()) {
                        return null;
                    }
                    Usuario u = opt.get();
                    return new MembroProjetoDTO(u.getId(), u.getNome(), u.getEmail(), u.getId().equals(criadorId));
                })
                .filter(m -> m != null)
                .sorted(Comparator.comparing(MembroProjetoDTO::criador).reversed()
                        .thenComparing(MembroProjetoDTO::nome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public void adicionarMembro(String projetoId, String usuarioId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        List<String> usuarios = projeto.getUsuarioIds();
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            projeto.setUsuarioIds(usuarios);
        }

        if (!usuarios.contains(usuarioId)) {
            usuarios.add(usuarioId);
            projetoRepositorio.save(projeto);
        }
    }

    public void removerMembro(String projetoId, String usuarioId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        List<String> usuarios = projeto.getUsuarioIds();
        if (usuarios == null || usuarios.isEmpty()) {
            return;
        }

        if (!usuarios.isEmpty() && usuarios.get(0).equals(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível remover o criador do projeto");
        }

        boolean removed = usuarios.remove(usuarioId);
        if (removed) {
            projetoRepositorio.save(projeto);
        }
    }
}
