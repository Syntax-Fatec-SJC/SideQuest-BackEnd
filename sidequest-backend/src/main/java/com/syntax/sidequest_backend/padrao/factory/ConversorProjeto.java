package com.syntax.sidequest_backend.padrao.factory;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;

class ConversorProjeto implements ConversorFactory<Projeto, ProjetoDTO> {
    
    @Override
    public Projeto converterParaEntidade(ProjetoDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setId(dto.getId());
        projeto.setNome(dto.getNome());
        projeto.setStatus(dto.getStatus());
        projeto.setUsuarioIds(dto.getUsuarioIds());
        return projeto;
    }
    
    @Override
    public ProjetoDTO converterParaDTO(Projeto entidade) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setStatus(entidade.getStatus());
        dto.setUsuarioIds(entidade.getUsuarioIds());
        return dto;
    }
}
