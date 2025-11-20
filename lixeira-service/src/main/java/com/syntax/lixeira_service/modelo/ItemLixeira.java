package com.syntax.lixeira_service.modelo;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "itens_lixeira")
public class ItemLixeira {

    @Id
    private String id;
    private TipoItem tipoItem;
    private String itemId;
    private String dadosOriginais;
    private String deletadoPor;
    private String emailDeletadoPor;
    private Date deletadoEm;
    private Date restauradoEm;
    private StatusLixeira status;
    private String nomeItem;

    // Construtores
    public ItemLixeira() {
    }

    public ItemLixeira(String id, TipoItem tipoItem, String itemId, String dadosOriginais,
            String deletadoPor, String emailDeletadoPor, Date deletadoEm,
            Date restauradoEm, StatusLixeira status, String nomeItem) {
        this.id = id;
        this.tipoItem = tipoItem;
        this.itemId = itemId;
        this.dadosOriginais = dadosOriginais;
        this.deletadoPor = deletadoPor;
        this.emailDeletadoPor = emailDeletadoPor;
        this.deletadoEm = deletadoEm;
        this.restauradoEm = restauradoEm;
        this.status = status;
        this.nomeItem = nomeItem;
    }

    // Getters
    public String getId() {
        return id;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public String getItemId() {
        return itemId;
    }

    public String getDadosOriginais() {
        return dadosOriginais;
    }

    public String getDeletadoPor() {
        return deletadoPor;
    }

    public String getEmailDeletadoPor() {
        return emailDeletadoPor;
    }

    public Date getDeletadoEm() {
        return deletadoEm;
    }

    public Date getRestauradoEm() {
        return restauradoEm;
    }

    public StatusLixeira getStatus() {
        return status;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setDadosOriginais(String dadosOriginais) {
        this.dadosOriginais = dadosOriginais;
    }

    public void setDeletadoPor(String deletadoPor) {
        this.deletadoPor = deletadoPor;
    }

    public void setEmailDeletadoPor(String emailDeletadoPor) {
        this.emailDeletadoPor = emailDeletadoPor;
    }

    public void setDeletadoEm(Date deletadoEm) {
        this.deletadoEm = deletadoEm;
    }

    public void setRestauradoEm(Date restauradoEm) {
        this.restauradoEm = restauradoEm;
    }

    public void setStatus(StatusLixeira status) {
        this.status = status;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    // Enums
    public enum TipoItem {
        TAREFA, PROJETO
    }

    public enum StatusLixeira {
        NA_LIXEIRA, RESTAURADO, DELETADO_PERMANENTEMENTE
    }
}
