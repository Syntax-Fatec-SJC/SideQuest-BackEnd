package com.syntax.api_gateway.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utilitário para extrair e propagar headers de autenticação aos microserviços
 */
public class HeaderPropagador {
    
    private final String userId;
    private final String userEmail;
    private final String userName;
    private final String gatewaySecret;
    
    private HeaderPropagador(String userId, String userEmail, String userName, String gatewaySecret) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.gatewaySecret = gatewaySecret;
    }
    
    /**
     * Extrai headers de autenticação dos atributos do request
     * (os atributos são adicionados pelo JwtAuthenticationFilter)
     */
    public static HeaderPropagador extrairDe(HttpServletRequest request) {
        String userId = (String) request.getAttribute("X-User-Id");
        String userEmail = (String) request.getAttribute("X-User-Email");
        String userName = (String) request.getAttribute("X-User-Name");
        String gatewaySecret = (String) request.getAttribute("X-Gateway-Secret");
        
        return new HeaderPropagador(userId, userEmail, userName, gatewaySecret);
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getGatewaySecret() {
        return gatewaySecret;
    }
}
