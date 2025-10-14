package com.syntax.sidequest_backend.modelo.dto.RespostaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroRespostaDTO {
    private String titulo;
    private String mensagem;
}
