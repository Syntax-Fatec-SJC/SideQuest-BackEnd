package com.fatec.anexo_service.entidade;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "anexos")
public class Anexo {

    @Id
    private String id;

    private String tarefaId;
    private String nome;
    private String contentType;
    private Long tamanho;
    private String gridFsFileId;
    private LocalDateTime dataUpload;

    public Anexo() {
        this.dataUpload = LocalDateTime.now();
    }

    public Anexo(String tarefaId, String nome, String contentType, Long tamanho, String gridFsFileId) {
        this.tarefaId = tarefaId;
        this.nome = nome;
        this.contentType = contentType;
        this.tamanho = tamanho;
        this.gridFsFileId = gridFsFileId;
        this.dataUpload = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(String tarefaId) {
        this.tarefaId = tarefaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public String getGridFsFileId() {
        return gridFsFileId;
    }

    public void setGridFsFileId(String gridFsFileId) {
        this.gridFsFileId = gridFsFileId;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    @Override
    public String toString() {
        return "Anexo{"
                + "id='" + id + '\''
                + ", tarefaId='" + tarefaId + '\''
                + ", nome='" + nome + '\''
                + ", contentType='" + contentType + '\''
                + ", tamanho=" + tamanho
                + ", gridFsFileId='" + gridFsFileId + '\''
                + ", dataUpload=" + dataUpload
                + '}';
    }
}
