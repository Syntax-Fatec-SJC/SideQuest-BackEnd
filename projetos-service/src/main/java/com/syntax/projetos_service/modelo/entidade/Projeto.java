package com.syntax.projetos_service.modelo.entidade;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * ENTIDADE PROJETO
 * 
 * Representa um projeto no sistema.
 * Armazenada na collection "projetos" do MongoDB.
 */
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
