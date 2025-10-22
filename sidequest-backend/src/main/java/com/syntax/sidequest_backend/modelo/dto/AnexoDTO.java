package com.syntax.sidequest_backend.modelo.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AnexoDTO {

    private String id;
    private String tarefaId;
    private String nomeOriginal;
    private String contentType;
    private Long tamanho;
    private Date dataUpload;
    private String urlDownload;  // URL para baixar o arquivo
}
