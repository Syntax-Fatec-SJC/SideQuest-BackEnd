package com.syntax.api_gateway.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utilitário para extrair e propagar headers de autenticação aos microserviços
 */
public class HeaderPropagador {
    
    private final String userId;
    private final String userEmail;
    private final String gatewaySecret;
    
    private HeaderPropagador(String userId, String userEmail, String gatewaySecret) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.gatewaySecret = gatewaySecret;
    }
    
    /**
     * Extrai headers de autenticação dos atributos do request
     * (os atributos são adicionados pelo JwtAuthenticationFilter)
     */
    public static HeaderPropagador extrairDe(HttpServletRequest request) {
        String userId = (String) request.getAttribute("X-User-Id");
        String userEmail = (String) request.getAttribute("X-User-Email");
        String gatewaySecret = (String) request.getAttribute("X-Gateway-Secret");
        
        return new HeaderPropagador(userId, userEmail, gatewaySecret);
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public String getGatewaySecret() {
        return gatewaySecret;
    }
}
