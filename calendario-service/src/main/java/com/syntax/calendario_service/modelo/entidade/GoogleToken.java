package com.syntax.calendario_service.modelo.entidade;

import org.springframework.data.couchbase.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
public class GoogleToken {
    @Id
    private String id;
    private Long usuarioId;
    private String accessToken;
    private String refreshToken;
    private Long expiresInseconds;
    private Long issuedAt;
}
