package com.syntax.sidequest_backend.modelo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnexoDTO {

    private String id;
    private String tarefaId;
    private String nomeOriginal;
    private String contentType;
    private Long tamanho;
    private Date dataUpload;
    private String urlDownload;
    private String statusTarefa;
    private String nomeTarefa;
    private String descricaoTarefa;
    private String comentarioTarefa;
}
