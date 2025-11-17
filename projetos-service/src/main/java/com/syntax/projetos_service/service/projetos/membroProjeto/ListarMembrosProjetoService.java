package com.syntax.projetos_service.service.projetos.membroProjeto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.client.UsuarioClient;
import com.syntax.projetos_service.modelo.dto.projetoDTO.MembroDetalhadoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para listar membros do projeto
 */
@Service
public class ListarMembrosProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private UsuarioClient usuarioClient;

    /**
     * Lista todos os membros do projeto com informações completas
     */
    public List<MembroDetalhadoDTO> executar(String projetoId) {
        Projeto projeto = projetoRepositorio.findById(projetoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Projeto não encontrado"));
        
        List<String> usuarioIds = projeto.getUsuarioIds();
        
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        // O primeiro usuário da lista é sempre o criador do projeto
        String criadorId = usuarioIds.get(0);
        
        return usuarioIds.stream()
                .map(usuarioId -> {
                    try {
                        var usuario = usuarioClient.buscarUsuario(usuarioId);
                        if (usuario != null) {
                            return new MembroDetalhadoDTO(
                                    usuarioId,
                                    usuario.getNome(),
                                    usuario.getEmail(),
                                    usuarioId.equals(criadorId)
                            );
                        } else {
                            // Fallback se usuário não for encontrado
                            return new MembroDetalhadoDTO(
                                    usuarioId,
                                    "Usuário não encontrado",
                                    "",
                                    usuarioId.equals(criadorId)
                            );
                        }
                    } catch (Exception e) {
                        // Fallback em caso de erro na comunicação
                        return new MembroDetalhadoDTO(
                                usuarioId,
                                "Erro ao carregar usuário",
                                "",
                                usuarioId.equals(criadorId)
                        );
                    }
                })
                .collect(Collectors.toList());
    }
}
