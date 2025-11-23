package com.syntax.projetos_service.modelo.dto.projetoDTO;

/**
 * DTO para representar um membro do projeto com informações completas
 */
public class MembroDetalhadoDTO {
    private String usuarioId;
    private String nome;
    private String email;
    private boolean criador;

    public MembroDetalhadoDTO() {
    }

    public MembroDetalhadoDTO(String usuarioId, String nome, String email, boolean criador) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.criador = criador;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCriador() {
        return criador;
    }

    public void setCriador(boolean criador) {
        this.criador = criador;
    }
}
