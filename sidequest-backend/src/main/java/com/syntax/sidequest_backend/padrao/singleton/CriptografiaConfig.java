package com.syntax.sidequest_backend.padrao.singleton;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CriptografiaConfig {
    
    private static CriptografiaConfig instancia;
    private final BCryptPasswordEncoder encoder;
    
    private CriptografiaConfig() {
        this.encoder = new BCryptPasswordEncoder();
    }
    
    public static synchronized CriptografiaConfig obterInstancia() {
        if (instancia == null) {
            instancia = new CriptografiaConfig();
        }
        return instancia;
    }
    
    public String criptografar(String texto) {
        return encoder.encode(texto);
    }
    
    public boolean verificar(String textoPlano, String textoCriptografado) {
        return encoder.matches(textoPlano, textoCriptografado);
    }
}
