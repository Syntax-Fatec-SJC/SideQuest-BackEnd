package com.syntax.sidequest_backend.modelo.dto.tarefasDTO;

import java.util.List;

public class AtualizarResponsaveisDTO {

    private List<String> usuarioIds;

    public List<String> getUsuarioIds() {
        return usuarioIds;
    }

    public void setUsuarioIds(List<String> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }
}
