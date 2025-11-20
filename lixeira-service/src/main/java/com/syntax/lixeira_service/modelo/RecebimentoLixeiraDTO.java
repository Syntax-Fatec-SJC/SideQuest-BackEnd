package com.syntax.lixeira_service.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para receber itens deletados dos outros microsserviços
 */
public class RecebimentoLixeiraDTO {

    @JsonProperty("tipoItem")
    private String tipoItem;

    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("nomeItem")
    private String nomeItem;

    @JsonProperty("dados")
    private Object dados;

    @JsonProperty("deletadoPor")
    private String deletadoPor;

    @JsonProperty("emailDeletadoPor")
    private String emailDeletadoPor;

    // ========== GETTERS ==========
    public String getTipoItem() {
        return tipoItem;
    }

    public String getItemId() {
        return itemId;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public Object getDados() {
        return dados;
    }

    public String getDeletadoPor() {
        return deletadoPor;
    }

    public String getEmailDeletadoPor() {
        return emailDeletadoPor;
    }

    // ========== SETTERS ==========
    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }

    public void setDeletadoPor(String deletadoPor) {
        this.deletadoPor = deletadoPor;
    }

    public void setEmailDeletadoPor(String emailDeletadoPor) {
        this.emailDeletadoPor = emailDeletadoPor;
    }
}
