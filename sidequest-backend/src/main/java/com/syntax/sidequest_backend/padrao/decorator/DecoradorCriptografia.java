package com.syntax.sidequest_backend.padrao.decorator;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.padrao.singleton.CriptografiaConfig;

public class DecoradorCriptografia implements ComponenteUsuario {
    
    private final ComponenteUsuario componente;
    
    public DecoradorCriptografia(ComponenteUsuario componente) {
        this.componente = componente;
    }
    
    @Override
    public Usuario processar(Usuario usuario) {
        Usuario processado = componente.processar(usuario);
        
        if (processado.getSenha() != null && !processado.getSenha().startsWith("$2a$")) {
            String senhaCriptografada = CriptografiaConfig.obterInstancia()
                .criptografar(processado.getSenha());
            processado.setSenha(senhaCriptografada);
        }
        
        return processado;
    }
}
