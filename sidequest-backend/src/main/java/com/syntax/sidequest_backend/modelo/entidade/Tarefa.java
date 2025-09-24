package com.syntax.sidequest_backend.modelo.entidade;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tarefas")
public class Tarefa {

    @Id
    private String id;
    private String nome;
    private Date prazoFinal;
    private String status;
    private String comentario;
    private String descricao;
    private String projetoId;
    private List<String> anexo;
    private List<String> usuariosIds;

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Date getPrazoFinal() {
        return prazoFinal;
    }

    public String getStatus() {
        return status;
    }

    public String getComentario() {
        return comentario;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getProjetoId() {
        return projetoId;
    }

    public List<String> getAnexo() {
        return anexo;
    }

    public List<String> getUsuariosIds() {
        return usuariosIds;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrazoFinal(Date prazoFinal) {
        this.prazoFinal = prazoFinal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setProjetoId(String projetoId) {
        this.projetoId = projetoId;
    }

    public void setAnexo(List<String> anexo) {
        this.anexo = anexo;
    }

    public void setUsuariosIds(List<String> usuariosIds) {
        this.usuariosIds = usuariosIds;
    }
}
