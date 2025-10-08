package com.syntax.sidequest_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.excecao.personalizado.CredenciaisInvalidasException;
import com.syntax.sidequest_backend.excecao.personalizado.UsuarioExistenteException;
import com.syntax.sidequest_backend.modelo.dto.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;
import com.syntax.sidequest_backend.padrao.decorator.ComponenteUsuario;
import com.syntax.sidequest_backend.padrao.decorator.DecoradorCriptografia;
import com.syntax.sidequest_backend.padrao.decorator.DecoradorValidacao;
import com.syntax.sidequest_backend.padrao.decorator.UsuarioBasico;
import com.syntax.sidequest_backend.padrao.factory.ConversorFactory;
import com.syntax.sidequest_backend.padrao.singleton.CriptografiaConfig;
import com.syntax.sidequest_backend.padrao.strategy.validacao.ValidacaoUsuario;
import com.syntax.sidequest_backend.padrao.template.ServicoTemplate;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;
import com.syntax.sidequest_backend.service.interfaces.IUsuarioService;

@Service
public class UsuarioService extends ServicoTemplate<Usuario, UsuarioDTO> implements IUsuarioService {
    
    private final UsuarioRepositorio repositorio;
    private final ConversorFactory<Usuario, UsuarioDTO> conversor;
    private final ValidacaoUsuario validacao;
    private final CriptografiaConfig criptografia;
    private final ComponenteUsuario processadorUsuario;

    public UsuarioService(UsuarioRepositorio repositorio, ValidacaoUsuario validacao) {
        this.repositorio = repositorio;
        this.conversor = ConversorFactory.criarConversorUsuario();
        this.validacao = validacao;
        this.criptografia = CriptografiaConfig.obterInstancia();
        
        this.processadorUsuario = new DecoradorCriptografia(
            new DecoradorValidacao(new UsuarioBasico())
        );
    }

    @Override
    public LoginResponseDTO realizarLogin(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = repositorio.findByEmail(loginDTO.getEmail());
        
        if (usuarioOpt.isEmpty()) {
            throw new CredenciaisInvalidasException("Email ou senha inválidos");
        }
        
        Usuario usuario = usuarioOpt.get();
        boolean senhaCorreta = criptografia.verificar(
            loginDTO.getSenha(),
            usuario.getSenha()
        );
        
        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException("Email ou senha inválidos");
        }
        
        return new LoginResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    @Override
    protected void validarAntesDeGravar(UsuarioDTO dto) {
        validacao.validar(dto);
    }

    @Override
    protected Usuario converterParaEntidade(UsuarioDTO dto) {
        return conversor.converterParaEntidade(dto);
    }

    @Override
    protected Usuario executarOperacaoEspecificaCriacao(Usuario entidade, UsuarioDTO dto) {
        if (repositorio.existsByEmail(dto.getEmail())) {
            throw new UsuarioExistenteException("Email já está em uso");
        }
        return processadorUsuario.processar(entidade);
    }

    @Override
    protected Usuario executarOperacaoEspecificaAtualizacao(Usuario entidade, UsuarioDTO dto) {
        return processadorUsuario.processar(entidade);
    }

    @Override
    protected Usuario salvar(Usuario entidade) {
        return repositorio.save(entidade);
    }

    @Override
    protected void validarAntesDeExcluir(String id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    @Override
    protected void executarExclusao(String id) {
        repositorio.deleteById(id);
    }

    @Override
    protected List<Usuario> buscarTodos() {
        return repositorio.findAll();
    }
}
