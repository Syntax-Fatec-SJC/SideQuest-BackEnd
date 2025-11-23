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
    private String tipo; // image, pdf, video
    private String contentType;
    private Long tamanho;
    private String tamanhoFormatado;
    private LocalDateTime dataUpload;

    // Armazena o arquivo em Base64 (para arquivos pequenos/médios)
    private String arquivoBase64;

    // Construtor vazio
    public Anexo() {
        this.dataUpload = LocalDateTime.now();
    }

    // Construtor completo
    public Anexo(String tarefaId, String nome, String tipo, String contentType, Long tamanho, String arquivoBase64) {
        this.tarefaId = tarefaId;
        this.nome = nome;
        this.tipo = tipo;
        this.contentType = contentType;
        this.tamanho = tamanho;
        this.tamanhoFormatado = formatarTamanho(tamanho);
        this.arquivoBase64 = arquivoBase64;
        this.dataUpload = LocalDateTime.now();
    }

    // Formatar tamanho do arquivo
    private String formatarTamanho(Long bytes) {
        if (bytes == null) {
            return "0 B";
        }
        if (bytes < 1024) {
            return bytes + " B";
        }
        if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        }
        if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", bytes / (1024.0 * 1024));
        }
        return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
    }

    // setters e setters
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

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
        this.tamanhoFormatado = formatarTamanho(tamanho);
    }

    public String getTamanhoFormatado() {
        return tamanhoFormatado;
    }

    public void setTamanhoFormatado(String tamanhoFormatado) {
        this.tamanhoFormatado = tamanhoFormatado;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public String getArquivoBase64() {
        return arquivoBase64;
    }

    public void setArquivoBase64(String arquivoBase64) {
        this.arquivoBase64 = arquivoBase64;
    }
}
