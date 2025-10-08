package com.syntax.sidequest_backend.service.interfaces;

import java.util.List;

import com.syntax.sidequest_backend.modelo.dto.MembroProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;

public interface IProjetoService extends ServicoBase<Projeto, ProjetoDTO> {
    Projeto criarComCriador(ProjetoDTO projetoDto, String usuarioIdCriador);
    List<Projeto> listarPorUsuario(String usuarioId);
    List<MembroProjetoDTO> listarMembros(String projetoId);
    void adicionarMembro(String projetoId, String usuarioId);
    void removerMembro(String projetoId, String usuarioId);
}
