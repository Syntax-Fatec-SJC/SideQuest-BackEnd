package com.syntax.lixeira_service.modelo;

import java.util.Date;
import java.util.List;

public class TarefaDTO {

    private String id;
    private String nome;
    private Date prazoFinal;
    private String status;
    private String comentario;
    private String descricao;
    private String projetoId;
    private List<String> anexos;
    private List<String> usuarioIds;

    // Construtores
    public TarefaDTO() {
    }

    public TarefaDTO(String id, String nome, Date prazoFinal, String status, String comentario,
            String descricao, String projetoId, List<String> anexos, List<String> usuarioIds) {
        this.id = id;
        this.nome = nome;
        this.prazoFinal = prazoFinal;
        this.status = status;
        this.comentario = comentario;
        this.descricao = descricao;
        this.projetoId = projetoId;
        this.anexos = anexos;
        this.usuarioIds = usuarioIds;
    }

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

    public List<String> getAnexos() {
        return anexos;
    }

    public List<String> getUsuarioIds() {
        return usuarioIds;
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

    public void setAnexos(List<String> anexos) {
        this.anexos = anexos;
    }

    public void setUsuarioIds(List<String> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }
}
