package com.syntax.sidequest_backend.padrao.factory;

import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.TarefaDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public interface ConversorFactory<E, D> {
    E converterParaEntidade(D dto);
    D converterParaDTO(E entidade);
    
    static ConversorFactory<Usuario, UsuarioDTO> criarConversorUsuario() {
        return new ConversorUsuario();
    }
    
    static ConversorFactory<Projeto, ProjetoDTO> criarConversorProjeto() {
        return new ConversorProjeto();
    }
    
    static ConversorFactory<Tarefa, TarefaDTO> criarConversorTarefa() {
        return new ConversorTarefa();
    }
}
