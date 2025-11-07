package com.syntax.projetos_service.service.projetos;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.projetos_service.modelo.conversor.ConversorProjeto;
import com.syntax.projetos_service.modelo.conversor.ConversorProjetoDTO;
import com.syntax.projetos_service.modelo.dto.projetoDTO.ProjetoDTO;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Service para cadastrar novo projeto
 */
@Service
public class CadastrarProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    public ProjetoDTO executar(ProjetoDTO dto, String usuarioIdCriador) {
        if (usuarioIdCriador == null || usuarioIdCriador.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "ID do usuário criador é obrigatório");
        }

        Projeto projeto = new ConversorProjetoDTO().converter(dto);
        
        // Adicionar criador como primeiro membro
        if (projeto.getUsuarioIds() == null) {
            projeto.setUsuarioIds(new ArrayList<>());
        }
        if (!projeto.getUsuarioIds().contains(usuarioIdCriador)) {
            projeto.getUsuarioIds().add(0, usuarioIdCriador);
        }

        Projeto salvo = projetoRepositorio.save(projeto);
        return ConversorProjeto.converter(salvo);
    }
}
