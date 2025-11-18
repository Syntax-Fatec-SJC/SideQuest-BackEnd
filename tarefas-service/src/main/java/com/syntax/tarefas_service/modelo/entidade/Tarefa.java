package com.syntax.tarefas_service.modelo.entidade;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * ENTIDADE TAREFA
 * 
 * Representa uma tarefa no sistema.
 * Armazenada na collection "tarefas" do MongoDB.
 */
@Data
@Document(collection = "tarefas")
public class Tarefa {

    @Id
    private String id;
    private String nome;
    private Date prazoFinal;
    private String status;
    private String comentario;
    private String descricao;
    private String projetoId;
    private List<String> anexos;
    private List<String> usuarioIds;
}
