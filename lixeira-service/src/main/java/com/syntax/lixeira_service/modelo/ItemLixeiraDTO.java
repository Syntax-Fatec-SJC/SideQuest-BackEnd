package com.syntax.lixeira_service.modelo;

import java.util.Date;
import com.syntax.lixeira_service.modelo.ItemLixeira.StatusLixeira;
import com.syntax.lixeira_service.modelo.ItemLixeira.TipoItem;

public class ItemLixeiraDTO {

    private String id;
    private TipoItem tipoItem;
    private String itemId;
    private String nomeItem;
    private String deletadoPor;
    private String emailDeletadoPor;
    private Date deletadoEm;
    private Date restauradoEm;
    private StatusLixeira status;
    private Object dadosOriginais;

    // Construtores
    public ItemLixeiraDTO() {
    }

    public ItemLixeiraDTO(String id, TipoItem tipoItem, String itemId, String nomeItem,
            String deletadoPor, String emailDeletadoPor, Date deletadoEm,
            Date restauradoEm, StatusLixeira status, Object dadosOriginais) {
        this.id = id;
        this.tipoItem = tipoItem;
        this.itemId = itemId;
        this.nomeItem = nomeItem;
        this.deletadoPor = deletadoPor;
        this.emailDeletadoPor = emailDeletadoPor;
        this.deletadoEm = deletadoEm;
        this.restauradoEm = restauradoEm;
        this.status = status;
        this.dadosOriginais = dadosOriginais;
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

    public String getNomeItem() {
        return nomeItem;
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

    public Object getDadosOriginais() {
        return dadosOriginais;
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

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
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

    public void setDadosOriginais(Object dadosOriginais) {
        this.dadosOriginais = dadosOriginais;
    }
}
