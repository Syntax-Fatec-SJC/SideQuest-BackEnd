package com.syntax.sidequest_backend.modelo.entidade;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "projetos")
public class Projeto {
    @Id
    private String id;
    private String status;
    private String nome;
    private String descricao;
    private Date prazoFinal;
    private List<String> usuarioIds;
}
