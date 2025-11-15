package com.fatec.anexo_service.entidade;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "anexos")
public class Anexo {

    public enum TipoAnexo {
        image, pdf, video
    }

    @Id
    private String id;

    private String fileName;
    private String contentType;
    private Long fileSize;
    private TipoAnexo tipo;
    private byte[] fileData;
    private String tarefaId;
    private LocalDateTime dataUpload;
}
