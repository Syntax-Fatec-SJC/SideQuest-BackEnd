package com.syntax.sidequest_backend.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handler que gerencia o sucesso da autenticação OAuth2 (Google)
 * Cria ou atualiza o usuário e redireciona com token JWT
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Extrai informações do Google
        String email = oAuth2User.getAttribute("email");
        String nome = oAuth2User.getAttribute("name");
        String fotoPerfil = oAuth2User.getAttribute("picture");
        String googleId = oAuth2User.getAttribute("sub");

        // Busca ou cria o usuário
        Usuario usuario = usuarioService.buscarOuCriarUsuarioOAuth2(email, nome, fotoPerfil, googleId);

        // Gera token JWT
        String token = jwtUtil.gerarToken(usuario.getEmail());

        // URL de redirecionamento com token
        String redirectUrl = getRedirectUrl() + "?token=" + token +
                           "&email=" + usuario.getEmail() +
                           "&nome=" + usuario.getNome();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String getRedirectUrl() {
        // Retorna a primeira URL permitida como padrão
        return allowedOrigins.split(",")[0] + "/auth/callback";
    }
}
