package com.syntax.projetos_service.service.projetos.membroProjeto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.client.AvisosClient;
import com.syntax.projetos_service.client.UsuariosClient;
import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.MembroProjetoDTO;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para adicionar membros ao projeto
 */
@Service
public class AdicionarMembroProjetoService {

    private static final Logger logger = LoggerFactory.getLogger(AdicionarMembroProjetoService.class);

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private AvisosClient avisosClient;

    @Autowired
    private UsuariosClient usuariosClient;

    /**
     * Adiciona membro(s) ao projeto
     */
    public ProjetoDTO executar(String projetoId, MembroProjetoDTO dto, String autorId, String autorNome) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Projeto não encontrado"));

        if (projeto.getUsuarioIds() == null) {
            projeto.setUsuarioIds(new ArrayList<>());
        }

        List<String> novosMembrosIds = new ArrayList<>();
        
        // Adicionar múltiplos usuários se fornecidos
        if (dto.getUsuarioIds() != null) {
            for (String usuarioId : dto.getUsuarioIds()) {
                if (!projeto.getUsuarioIds().contains(usuarioId)) {
                    projeto.getUsuarioIds().add(usuarioId);
                    novosMembrosIds.add(usuarioId);
                }
            }
        }

        Projeto salvo = projetoRepositorio.save(projeto);
        
        // Criar avisos apenas para os NOVOS membros adicionados
        if (autorNome != null && !autorNome.isBlank() && !novosMembrosIds.isEmpty()) {
            logger.info("🔔 Criando avisos para {} novos membros adicionados ao projeto {}", novosMembrosIds.size(), projetoId);
            
            // Cada novo membro recebe UM aviso sobre ter sido adicionado
            for (String novoMembroId : novosMembrosIds) {
                String novoMembroNome = usuariosClient.buscarNomeUsuario(novoMembroId);
                if (novoMembroNome == null) {
                    novoMembroNome = "um usuário";
                }
                
                logger.info("🔔 Criando aviso para novo membro: {} ({})", novoMembroId, novoMembroNome);
                avisosClient.criarAvisoNovoMembroProjeto(
                    projetoId,
                    novoMembroId,
                    autorId,
                    autorNome
                );
            }
            
            // Cria aviso também para o autor se ele não estiver no projeto
            if (autorId != null && !salvo.getUsuarioIds().contains(autorId)) {
                logger.info("🔔 Criando aviso para o autor: {} ({})", autorId, autorNome);
                // Para o autor, cria avisos sobre cada membro que ele adicionou
                for (String novoMembroId : novosMembrosIds) {
                    String novoMembroNome = usuariosClient.buscarNomeUsuario(novoMembroId);
                    if (novoMembroNome == null) {
                        novoMembroNome = "um usuário";
                    }
                    avisosClient.criarAvisoMembroAdicionadoProjeto(
                        projetoId,
                        autorId,
                        autorId,
                        autorNome,
                        novoMembroId,
                        novoMembroNome
                    );
                }
            }
        }
        
        return ConversorProjeto.converter(salvo);
    }
}
