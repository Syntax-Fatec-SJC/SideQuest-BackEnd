package com.syntax.sidequest_backend.service;

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
        projeto.setUsuariosIds(projetoDTO.getUsuariosIds());
        projeto.setTarefasIds(projetoDTO.getTarefasIds());
        return projeto;
    }

    public Projeto criarProjeto(ProjetoDTO projetoDto) {
        Projeto projeto = converterProjetoDTO(projetoDto);
        Projeto projetoSalvo = repositorio.save(projeto);
        return projetoSalvo;
    }

    public Projeto atualizarProjeto(ProjetoDTO projetoDto) {
        Projeto projeto = converterProjetoDTO(projetoDto);
        Projeto projetoAtualizado = repositorio.save(projeto);
        return projetoAtualizado;
    }

    public void excluirProjeto(ProjetoDTO projetoDto) {
        repositorio.deleteById(projetoDto.getId());
    }
}
