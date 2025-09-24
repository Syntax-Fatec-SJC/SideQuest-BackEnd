package com.syntax.sidequest_backend.modelo.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entidade Usuario - representa um usuário no sistema
 * Pode ser criado via cadastro local (email/senha) ou via OAuth2 (Google)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    @Indexed(unique = true)
    private String email;

    // Senha pode ser null para usuários OAuth2
    private String senha;

    // Provedor de autenticação: "local" ou "google"
    private String provedor;

    // ID do usuário no provedor OAuth2 (se aplicável)
    private String provedorId;

    // Foto do perfil (URL)
    private String fotoPerfil;

    // Controle de ativação da conta
    private boolean ativo = true;

    // Timestamps
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    // Construtor para usuários locais
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.provedor = "local";
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Construtor para usuários OAuth2
    public Usuario(String nome, String email, String provedor, String provedorId, String fotoPerfil) {
        this.nome = nome;
        this.email = email;
        this.provedor = provedor;
        this.provedorId = provedorId;
        this.fotoPerfil = fotoPerfil;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
}
