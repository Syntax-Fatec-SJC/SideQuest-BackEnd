package com.syntax.api_gateway.seguranca;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Utilitário para validação de tokens JWT
 * IMPORTANTE: Deve usar a MESMA SECRET_KEY do Usuario-Service
 */
@Component
public class JwtUtil {

    // DEVE SER IGUAL ao Usuario-Service (em Base64)
    private static final String SECRET_KEY = "U2lkZVF1ZXN0U2VjcmV0S2V5MjAyNFNlY3VyaXR5S2V5Rm9ySldUVG9rZW5HZW5lcmF0aW9uQW5kVmFsaWRhdGlvbg==";

    /**
     * Obtém a chave secreta para validação do JWT
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extrai o email (subject) do token JWT
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extrai a data de expiração do token
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Extrai todos os claims do token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Verifica se o token está expirado
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valida o token JWT
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrai o ID do usuário do token
     */
    public String extractUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    /**
     * Extrai o nome do usuário do token
     */
    public String extractNome(String token) {
        return extractAllClaims(token).get("nome", String.class);
    }
}
