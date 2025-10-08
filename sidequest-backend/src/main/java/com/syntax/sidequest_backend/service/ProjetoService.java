package com.syntax.sidequest_backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.excecao.personalizado.OperacaoNaoPermitidaException;
import com.syntax.sidequest_backend.excecao.personalizado.ProjetoNaoEncontradoException;
import com.syntax.sidequest_backend.excecao.personalizado.UsuarioNaoEncontradoException;
import com.syntax.sidequest_backend.modelo.dto.MembroProjetoDTO;
import com.syntax.sidequest_backend.modelo.dto.ProjetoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Projeto;
import com.syntax.sidequest_backend.padrao.adapter.AdaptadorUsuarioParaMembro;
import com.syntax.sidequest_backend.padrao.factory.ConversorFactory;
import com.syntax.sidequest_backend.padrao.observer.GerenciadorEventos;
import com.syntax.sidequest_backend.padrao.observer.Observador;
import com.syntax.sidequest_backend.padrao.strategy.validacao.ValidacaoProjeto;
import com.syntax.sidequest_backend.padrao.template.ServicoTemplate;
import com.syntax.sidequest_backend.repositorio.ProjetoRepositorio;
import com.syntax.sidequest_backend.repositorio.UsuarioRepositorio;
import com.syntax.sidequest_backend.service.interfaces.IProjetoService;

/**
 * Serviço responsável pela lógica de negócio relacionada a Projetos.
 * 
 * Implementa o padrão Template Method através de ServicoTemplate e
 * utiliza diversos outros padrões de design (Observer, Factory, Strategy, Adapter).
 * 
 * @author SideQuest Team
 */
@Service
public class ProjetoService extends ServicoTemplate<Projeto, ProjetoDTO> implements IProjetoService {
    
    /**
     * Índice que representa a posição do criador na lista de usuários do projeto.
     * O criador é sempre o primeiro usuário (índice 0) da lista.
     */
    private static final int INDICE_CRIADOR = 0;
    
    private final ProjetoRepositorio repositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ConversorFactory<Projeto, ProjetoDTO> conversor;
    private final ValidacaoProjeto validacao;
    private final GerenciadorEventos<Projeto> gerenciadorEventos;

    public ProjetoService(
            ProjetoRepositorio repositorio,
            UsuarioRepositorio usuarioRepositorio,
            ValidacaoProjeto validacao,
            Observador<Projeto> observador) {
        this.repositorio = repositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.conversor = ConversorFactory.criarConversorProjeto();
        this.validacao = validacao;
        this.gerenciadorEventos = new GerenciadorEventos<>();
        this.gerenciadorEventos.registrarObservador(observador);
    }

    @Override
    public Projeto criarComCriador(ProjetoDTO projetoDto, String usuarioIdCriador) {
        garantirListaUsuariosInicializada(projetoDto);
        adicionarCriadorSeNecessario(projetoDto, usuarioIdCriador);

        Projeto projeto = criar(projetoDto);
        gerenciadorEventos.notificar("PROJETO_CRIADO", projeto);
        return projeto;
    }

    @Override
    public List<Projeto> listarPorUsuario(String usuarioId) {
        return repositorio.findByUsuarioIdsContaining(usuarioId);
    }

    @Override
    public List<MembroProjetoDTO> listarMembros(String projetoId) {
        Projeto projeto = buscarProjetoPorId(projetoId);

        List<String> usuarioIds = projeto.getUsuarioIds();
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            return List.of();
        }

        String criadorId = obterIdCriador(projeto);

        return usuarioIds.stream()
            .map(usuarioId -> usuarioRepositorio.findById(usuarioId).orElse(null))
            .filter(usuario -> usuario != null)
            .map(usuario -> AdaptadorUsuarioParaMembro.adaptar(
                usuario, 
                usuario.getId().equals(criadorId)))
            .sorted(Comparator.comparing(MembroProjetoDTO::criador).reversed()
                    .thenComparing(MembroProjetoDTO::nome, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    @Override
    public void adicionarMembro(String projetoId, String usuarioId) {
        Projeto projeto = buscarProjetoPorId(projetoId);
        validarUsuarioExiste(usuarioId);

        List<String> usuarios = garantirListaUsuariosNoProjeto(projeto);

        if (usuarios.contains(usuarioId)) {
            throw new OperacaoNaoPermitidaException(
                "Usuário já é membro do projeto"
            );
        }

        usuarios.add(usuarioId);
        repositorio.save(projeto);
        gerenciadorEventos.notificar("MEMBRO_ADICIONADO", projeto);
    }

    @Override
    public void removerMembro(String projetoId, String usuarioId) {
        Projeto projeto = buscarProjetoPorId(projetoId);

        List<String> usuarios = projeto.getUsuarioIds();
        if (usuarios == null || usuarios.isEmpty()) {
            throw new OperacaoNaoPermitidaException(
                "Projeto não possui membros"
            );
        }

        validarNaoRemoverCriador(usuarios, usuarioId);

        boolean removido = usuarios.remove(usuarioId);
        if (removido) {
            repositorio.save(projeto);
            gerenciadorEventos.notificar("MEMBRO_REMOVIDO", projeto);
        } else {
            throw new OperacaoNaoPermitidaException(
                "Usuário não é membro do projeto"
            );
        }
    }

    @Override
    protected void validarAntesDeGravar(ProjetoDTO dto) {
        validacao.validar(dto);
    }

    @Override
    protected Projeto converterParaEntidade(ProjetoDTO dto) {
        return conversor.converterParaEntidade(dto);
    }

    @Override
    protected Projeto salvar(Projeto entidade) {
        return repositorio.save(entidade);
    }

    @Override
    protected void validarAntesDeExcluir(String id) {
        if (!repositorio.existsById(id)) {
            throw new ProjetoNaoEncontradoException(id);
        }
    }

    @Override
    protected void executarExclusao(String id) {
        repositorio.deleteById(id);
    }

    @Override
    protected List<Projeto> buscarTodos() {
        return repositorio.findAll();
    }

    @Override
    protected void executarAposAtualizacao(Projeto entidade) {
        gerenciadorEventos.notificar("PROJETO_ATUALIZADO", entidade);
    }

    // ===== MÉTODOS AUXILIARES PRIVADOS =====

    /**
     * Busca um projeto por ID ou lança exceção se não encontrado.
     * 
     * @param projetoId ID do projeto
     * @return Projeto encontrado
     * @throws ProjetoNaoEncontradoException se projeto não existir
     */
    private Projeto buscarProjetoPorId(String projetoId) {
        return repositorio.findById(projetoId)
            .orElseThrow(() -> new ProjetoNaoEncontradoException(projetoId));
    }

    /**
     * Valida se um usuário existe no sistema.
     * 
     * @param usuarioId ID do usuário
     * @throws UsuarioNaoEncontradoException se usuário não existir
     */
    private void validarUsuarioExiste(String usuarioId) {
        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        }
    }

    /**
     * Garante que a lista de usuários do DTO não seja nula.
     * Inicializa com ArrayList vazio se necessário.
     * 
     * @param projetoDto DTO do projeto
     */
    private void garantirListaUsuariosInicializada(ProjetoDTO projetoDto) {
        if (projetoDto.getUsuarioIds() == null) {
            projetoDto.setUsuarioIds(new ArrayList<>());
        }
    }

    /**
     * Adiciona o criador na primeira posição da lista se ele ainda não estiver presente.
     * 
     * @param projetoDto DTO do projeto
     * @param usuarioIdCriador ID do usuário criador
     */
    private void adicionarCriadorSeNecessario(ProjetoDTO projetoDto, String usuarioIdCriador) {
        List<String> usuarios = projetoDto.getUsuarioIds();
        if (usuarios != null && !usuarios.contains(usuarioIdCriador)) {
            usuarios.add(INDICE_CRIADOR, usuarioIdCriador);
        }
    }

    /**
     * Garante que a lista de usuários do projeto não seja nula.
     * Inicializa e atribui ao projeto se necessário.
     * 
     * @param projeto Entidade do projeto
     * @return Lista de IDs de usuários (nunca nula)
     */
    private List<String> garantirListaUsuariosNoProjeto(Projeto projeto) {
        List<String> usuarios = projeto.getUsuarioIds();
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            projeto.setUsuarioIds(usuarios);
        }
        return usuarios;
    }

    /**
     * Obtém o ID do criador do projeto (sempre na primeira posição).
     * 
     * @param projeto Entidade do projeto
     * @return ID do usuário criador
     * @throws IllegalStateException se projeto não tiver criador
     */
    private String obterIdCriador(Projeto projeto) {
        List<String> usuarioIds = projeto.getUsuarioIds();
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            throw new IllegalStateException("Projeto sem criador definido");
        }
        return usuarioIds.get(INDICE_CRIADOR);
    }

    /**
     * Valida que o usuário a ser removido não é o criador do projeto.
     * 
     * @param usuarios Lista de IDs de usuários do projeto
     * @param usuarioId ID do usuário a ser removido
     * @throws OperacaoNaoPermitidaException se tentar remover o criador
     */
    private void validarNaoRemoverCriador(List<String> usuarios, String usuarioId) {
        if (!usuarios.isEmpty() && usuarios.get(INDICE_CRIADOR).equals(usuarioId)) {
            throw new OperacaoNaoPermitidaException(
                "Não é possível remover o criador do projeto"
            );
        }
    }
}
