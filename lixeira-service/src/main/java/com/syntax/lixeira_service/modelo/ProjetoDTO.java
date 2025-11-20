package com.syntax.lixeira_service.modelo;

import java.util.Date;
import java.util.List;

public class ProjetoDTO {

    private String id;
    private String status;
    private String nome;
    private String descricao;
    private Date prazoFinal;
    private List<String> usuarioIds;

    // Construtores
    public ProjetoDTO() {
    }

    public ProjetoDTO(String id, String status, String nome, String descricao,
            Date prazoFinal, List<String> usuarioIds) {
        this.id = id;
        this.status = status;
        this.nome = nome;
        this.descricao = descricao;
        this.prazoFinal = prazoFinal;
        this.usuarioIds = usuarioIds;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getPrazoFinal() {
        return prazoFinal;
    }

    public List<String> getUsuarioIds() {
        return usuarioIds;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPrazoFinal(Date prazoFinal) {
        this.prazoFinal = prazoFinal;
    }

    public void setUsuarioIds(List<String> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }
}
