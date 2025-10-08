package com.syntax.sidequest_backend.padrao.builder;

import java.util.ArrayList;
import java.util.List;

import com.syntax.sidequest_backend.modelo.entidade.Projeto;

public class ProjetoBuilder {
    
    private String id;
    private String nome;
    private String status;
    private List<String> usuarioIds;
    
    public ProjetoBuilder() {
        this.usuarioIds = new ArrayList<>();
        this.status = "ATIVO";
    }
    
    public ProjetoBuilder comId(String id) {
        this.id = id;
        return this;
    }
    
    public ProjetoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public ProjetoBuilder comStatus(String status) {
        this.status = status;
        return this;
    }
    
    public ProjetoBuilder adicionarUsuario(String usuarioId) {
        if (!this.usuarioIds.contains(usuarioId)) {
            this.usuarioIds.add(usuarioId);
        }
        return this;
    }
    
    public ProjetoBuilder comUsuarios(List<String> usuarioIds) {
        this.usuarioIds = usuarioIds != null ? new ArrayList<>(usuarioIds) : new ArrayList<>();
        return this;
    }
    
    public Projeto construir() {
        Projeto projeto = new Projeto();
        projeto.setId(this.id);
        projeto.setNome(this.nome);
        projeto.setStatus(this.status);
        projeto.setUsuarioIds(this.usuarioIds);
        return projeto;
    }
}
