package com.syntax.projetos_service.service.projetos.listarProjetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para listar projetos por usuário
 */
@Service
public class ListarPorUsuarioService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    public List<ProjetoDTO> executar(String usuarioId) {
        return projetoRepositorio.findByUsuarioIdsContaining(usuarioId).stream()
            .map(ConversorProjeto::converter)
            .toList();
    }
}
