package com.syntax.sidequest_backend.padrao.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.syntax.sidequest_backend.modelo.entidade.Tarefa;

public class TarefaBuilder {
    
    private String id;
    private String nome;
    private Date prazoFinal;
    private String status;
    private String comentario;
    private String descricao;
    private String projetoId;
    private List<String> anexos;
    private List<String> usuarioIds;
    
    public TarefaBuilder() {
        this.anexos = new ArrayList<>();
        this.usuarioIds = new ArrayList<>();
        this.status = "PENDENTE";
    }
    
    public TarefaBuilder comId(String id) {
        this.id = id;
        return this;
    }
    
    public TarefaBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public TarefaBuilder comPrazoFinal(Date prazoFinal) {
        this.prazoFinal = prazoFinal;
        return this;
    }
    
    public TarefaBuilder comStatus(String status) {
        this.status = status;
        return this;
    }
    
    public TarefaBuilder comComentario(String comentario) {
        this.comentario = comentario;
        return this;
    }
    
    public TarefaBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }
    
    public TarefaBuilder doProjeto(String projetoId) {
        this.projetoId = projetoId;
        return this;
    }
    
    public TarefaBuilder adicionarAnexo(String anexo) {
        this.anexos.add(anexo);
        return this;
    }
    
    public TarefaBuilder atribuirUsuario(String usuarioId) {
        if (!this.usuarioIds.contains(usuarioId)) {
            this.usuarioIds.add(usuarioId);
        }
        return this;
    }
    
    public Tarefa construir() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(this.id);
        tarefa.setNome(this.nome);
        tarefa.setPrazoFinal(this.prazoFinal);
        tarefa.setStatus(this.status);
        tarefa.setComentario(this.comentario);
        tarefa.setDescricao(this.descricao);
        tarefa.setProjetoId(this.projetoId);
        tarefa.setAnexos(this.anexos);
        tarefa.setUsuarioIds(this.usuarioIds);
        return tarefa;
    }
}
