package com.syntax.sidequest_backend.modelo.entidade;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "membros")
public class Membro {
    @Id
    private String id;
    private String nome;
    private String email;
    private String funcao;
}
