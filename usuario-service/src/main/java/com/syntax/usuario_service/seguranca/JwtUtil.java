package com.syntax.usuario_service.seguranca;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Deve ser a mesma chave do API Gateway (em Base64)
    private static final String SECRET_KEY = "U2lkZVF1ZXN0U2VjcmV0S2V5MjAyNFNlY3VyaXR5S2V5Rm9ySldUVG9rZW5HZW5lcmF0aW9uQW5kVmFsaWRhdGlvbg==";

    // Gera o token JWT com email, userId e nome
    public String generateToken(String email, String userId, String nome) {
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("nome", nome)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(getSignKey())
                .compact();
    }

    // Valida o token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrai o email (subject) do token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai o userId do token
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    // Extrai o nome do token
    public String extractNome(String token) {
        return extractClaim(token, claims -> claims.get("nome", String.class));
    }

    // Extrai uma claim específica do token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrai todas as claims do token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Obtém a chave de assinatura
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Verifica se o token expirou
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrai a data de expiração do token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
