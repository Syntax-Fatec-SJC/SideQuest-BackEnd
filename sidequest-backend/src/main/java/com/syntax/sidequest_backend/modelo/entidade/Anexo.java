package com.syntax.sidequest_backend.modelo.entidade;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "anexos")
public class Anexo {

    @Id
    private String id;

    private String tarefaId;           // ID da tarefa associada
    private String nomeOriginal;       // Nome original do arquivo
    private String contentType;        // Tipo MIME (image/png, video/mp4, etc)
    private Long tamanho;              // Tamanho em bytes
    private String gridFsFileId;       // ID do arquivo no GridFS
    private Date dataUpload;           // Data do upload
    private String uploadedBy;         // ID do usuário que fez upload (opcional)
}
