package com.syntax.sidequest_backend.modelo.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProjetoDTO {
    private String id;

    @NotBlank(message = "O status do projeto é obrigatório")
    private String status;

    @NotBlank(message = "O nome do projeto é obrigatório")
    private String nome;

    @NotEmpty(message = "É necessário informar pelo menos um usuário")
    private List<String> usuariosIds;

    private List<String> tarefasIds;
}

// dto login colocar refexxp 
// vc cria uma sua so quando nao ter uma reception que te atende
// validacao tanto no front tanto no back para nao quebrar no front