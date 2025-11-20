package com.syntax.tarefas_service.modelo.dto.tarefaDTO;

import java.time.LocalDateTime;

public class RecebimentoLixeiraDTO {

    private String tipoItem;
    private String itemId;
    private String nomeItem;
    private String status;
    private String deletadoPor;
    private String emailDeletadoPor;
    private LocalDateTime deletadoEm;
    private Object dados;

    public RecebimentoLixeiraDTO() {
        this.deletadoEm = LocalDateTime.now();
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeletadoPor() {
        return deletadoPor;
    }

    public void setDeletadoPor(String deletadoPor) {
        this.deletadoPor = deletadoPor;
    }

    public String getEmailDeletadoPor() {
        return emailDeletadoPor;
    }

    public void setEmailDeletadoPor(String emailDeletadoPor) {
        this.emailDeletadoPor = emailDeletadoPor;
    }

    public LocalDateTime getDeletadoEm() {
        return deletadoEm;
    }

    public void setDeletadoEm(LocalDateTime deletadoEm) {
        this.deletadoEm = deletadoEm;
    }

    public Object getDados() {
        return dados;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }
}
