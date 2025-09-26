package com.syntax.sidequest_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.excecao.AppExcecao;
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

    public List<Projeto> listarProjetos() {
        return repositorio.findAll();
    }

    public Projeto criarProjeto(ProjetoDTO projetoDto) {
        Projeto projeto = converterProjetoDTO(projetoDto);
        return repositorio.save(projeto);
    }

    public Projeto atualizarProjeto(ProjetoDTO projetoDto) {
        if (projetoDto.getId() == null || !repositorio.existsById(projetoDto.getId())) {
            throw new AppExcecao("Projeto não encontrado para atualização");
        }

        Projeto projeto = converterProjetoDTO(projetoDto);
        return repositorio.save(projeto);
    }

    public void excluirProjeto(String id) {
        if (!repositorio.existsById(id)) {
            throw new AppExcecao("Projeto não encontrado para exclusão");
        }

        repositorio.deleteById(id);
    }
}
