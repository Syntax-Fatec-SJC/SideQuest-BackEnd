package com.syntax.sidequest_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepositorio repositorio;

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
}
