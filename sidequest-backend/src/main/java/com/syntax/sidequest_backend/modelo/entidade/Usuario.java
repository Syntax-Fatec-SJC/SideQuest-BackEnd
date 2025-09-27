package com.syntax.sidequest_backend.modelo.entidade;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nome;
    private String email;
    private String senha;
    private List<String> projetosIds;
    private List<String> tarefasIds;
}
