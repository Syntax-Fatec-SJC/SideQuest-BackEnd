package com.syntax.avisos_service.modelo.entidade;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * ENTIDADE AVISO
 * 
 * Representa um aviso no sistema.
 * Armazenado na collection "avisos" do MongoDB.
 */
@Data
@Document(collection = "avisos")
public class Aviso {

    @Id
    private String id;
    
    private String tipo; // urgente, edicao, novo
    
    private String mensagem;
    
    private Date data;
    
    private Boolean visualizado;
    
    private String usuarioId; // ID do usuário que receberá o aviso
    
    private String tarefaId; // ID da tarefa relacionada (opcional)
    
    private String projetoId; // ID do projeto relacionado (opcional)
    
    private String autorId; // ID do usuário que causou o aviso (ex: quem editou a tarefa)
    
    private String autorNome; // Nome do usuário que causou o aviso
    
    private String membroAdicionadoId; // ID do membro que foi adicionado (quando aplicável)
    
    private String membroAdicionadoNome; // Nome do membro que foi adicionado (quando aplicável)
}
