package com.syntax.sidequest_backend.modelo.entidade;

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
    private List<String> usuariosIds;
    private List<String> tarefasIds;
}
