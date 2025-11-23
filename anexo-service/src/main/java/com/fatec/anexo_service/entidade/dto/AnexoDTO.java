package com.fatec.anexo_service.entidade.dto;

import java.util.Base64;

import com.fatec.anexo_service.entidade.Anexo;

public class AnexoDTO {

    private String id;
    private String tarefaId;
    private String nome;
    private String tipo;
    private String contentType;
    private String tamanho;
    private String dataUpload;
    private String arquivoBase64; // Incluído para download

    public AnexoDTO(Anexo entidade) {
        this.id = entidade.getId();
        this.tarefaId = entidade.getTarefaId();
        this.nome = entidade.getNome();
        this.tipo = entidade.getTipo();
        this.contentType = entidade.getContentType();
        this.tamanho = entidade.getTamanhoFormatado();
        this.dataUpload = entidade.getDataUpload() != null ? entidade.getDataUpload().toString() : null;
        this.arquivoBase64 = entidade.getArquivoBase64();
    }

    // Para download em byte[]
    public byte[] getBytes() {
        if (this.arquivoBase64 == null) {
            return new byte[0];
        }
        return Base64.getDecoder().decode(this.arquivoBase64);
    }

    // getters
    public String getId() {
        return id;
    }

    public String getTarefaId() {
        return tarefaId;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getContentType() {
        return contentType;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getDataUpload() {
        return dataUpload;
    }

    public String getArquivoBase64() {
        return arquivoBase64;
    }
}
