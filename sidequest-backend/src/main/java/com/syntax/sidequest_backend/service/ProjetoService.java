package com.syntax.sidequest_backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.MembroProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepositorio repositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private Projeto converterProjetoDTO(ProjetoDTO projetoDTO) {
        Projeto projeto = new Projeto();
        projeto.setId(projetoDTO.getId());
        projeto.setNome(projetoDTO.getNome());
        projeto.setStatus(projetoDTO.getStatus());
        projeto.setUsuarioIds(projetoDTO.getUsuarioIds());
        return projeto;
    }

    public Projeto criarProjeto(ProjetoDTO projetoDto, String usuarioIdCriador) {
        if (projetoDto.getUsuarioIds() == null) {
            projetoDto.setUsuarioIds(new ArrayList<>());
        }

        if (!projetoDto.getUsuarioIds().contains(usuarioIdCriador)) {
            projetoDto.getUsuarioIds().add(usuarioIdCriador);
        }

        Projeto projeto = converterProjetoDTO(projetoDto);
        return repositorio.save(projeto);
    }

    public List<Projeto> listarProjetos(){
        return repositorio.findAll();
    }

    public List<Projeto> listarProjetosPorUsuario(String usuarioId) {
        return repositorio.findByUsuarioIdsContaining(usuarioId);
    }

    public Projeto atualizarProjeto(ProjetoDTO projetoDto) {
        Projeto projeto = converterProjetoDTO(projetoDto);
        Projeto projetoAtualizado = repositorio.save(projeto);
        return projetoAtualizado;
    }

    public void excluirProjeto(ProjetoDTO projetoDto) {
        repositorio.deleteById(projetoDto.getId());
    }

    public void excluirProjetoPorId(String id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado");
        }
        repositorio.deleteById(id);
    }

    public List<MembroProjetoDTO> listarMembros(String projetoId) {
        Projeto projeto = repositorio.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (projeto.getUsuarioIds() == null || projeto.getUsuarioIds().isEmpty()) {
            return List.of();
        }

        String criadorId = projeto.getUsuarioIds().get(0); 

        List<MembroProjetoDTO> membros = projeto.getUsuarioIds().stream()
            .map(usuarioId -> {
                Optional<Usuario> opt = usuarioRepositorio.findById(usuarioId);
                if (opt.isEmpty()) return null; 
                Usuario u = opt.get();
                return new MembroProjetoDTO(u.getId(), u.getNome(), u.getEmail(), u.getId().equals(criadorId));
            })
            .filter(m -> m != null)
            .sorted(Comparator.comparing(MembroProjetoDTO::criador).reversed()
                    .thenComparing(MembroProjetoDTO::nome, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());

        return membros;
    }

    public void adicionarMembro(String projetoId, String usuarioId) {
        Projeto projeto = repositorio.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        List<String> usuarios = projeto.getUsuarioIds();
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            projeto.setUsuarioIds(usuarios);
        }

        if (usuarios.contains(usuarioId)) {
            return; 
        }

        usuarios.add(usuarioId);
        repositorio.save(projeto);
    }

    public void removerMembro(String projetoId, String usuarioId) {
        Projeto projeto = repositorio.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        List<String> usuarios = projeto.getUsuarioIds();
        if (usuarios == null || usuarios.isEmpty()) {
            return;
        }

        if (!usuarios.isEmpty() && usuarios.get(0).equals(usuarioId)) {
            throw new RuntimeException("Não é possível remover o criador do projeto");
        }

        boolean removed = usuarios.remove(usuarioId);
        if (removed) {
            repositorio.save(projeto);
        }
    }
}
