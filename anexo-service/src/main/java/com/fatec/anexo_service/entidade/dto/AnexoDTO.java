package com.fatec.anexo_service.entidade.dto;

import com.fatec.anexo_service.entidade.Anexo;

public class AnexoDTO {

    private String id;
    private String tarefaId;
    private String nome;
    private String tipo;
    private String contentType;
    private String tamanho;
    private String dataUpload;
    private String arquivoBase64;

    public AnexoDTO() {
    }

    public AnexoDTO(Anexo anexo) {
        this.id = anexo.getId();
        this.tarefaId = anexo.getTarefaId();
        this.nome = anexo.getNome();
        this.contentType = anexo.getContentType();
        this.tipo = determinarTipo(anexo.getContentType());
        this.tamanho = formatarTamanho(anexo.getTamanho());
        this.dataUpload = anexo.getDataUpload() != null ? anexo.getDataUpload().toString() : null;
        this.arquivoBase64 = null;
    }

    public AnexoDTO(Anexo anexo, String arquivoBase64) {
        this(anexo);
        this.arquivoBase64 = arquivoBase64;
    }

    private String determinarTipo(String contentType) {
        if (contentType == null) {
            return "image";
        }

        if (contentType.startsWith("image/")) {
            return "image";
        } else if (contentType.equals("application/pdf")) {
            return "pdf";
        } else if (contentType.startsWith("video/")) {
            return "video";
        }

        return "image";
    }

    private String formatarTamanho(Long bytes) {
        if (bytes == null || bytes == 0) {
            return "0 B";
        }

        double kb = bytes / 1024.0;
        if (kb < 1024) {
            return String.format("%.1f KB", kb);
        }

        double mb = kb / 1024.0;
        if (mb < 1024) {
            return String.format("%.1f MB", mb);
        }

        double gb = mb / 1024.0;
        return String.format("%.1f GB", gb);
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(String dataUpload) {
        this.dataUpload = dataUpload;
    }

    public String getArquivoBase64() {
        return arquivoBase64;
    }

    public void setArquivoBase64(String arquivoBase64) {
        this.arquivoBase64 = arquivoBase64;
    }

    @Override
    public String toString() {
        return "AnexoDTO{"
                + "id='" + id + '\''
                + ", tarefaId='" + tarefaId + '\''
                + ", nome='" + nome + '\''
                + ", tipo='" + tipo + '\''
                + ", contentType='" + contentType + '\''
                + ", tamanho='" + tamanho + '\''
                + ", dataUpload='" + dataUpload + '\''
                + ", temArquivo=" + (arquivoBase64 != null)
                + '}';
    }
}
