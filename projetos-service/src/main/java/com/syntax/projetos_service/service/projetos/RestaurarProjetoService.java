package com.syntax.projetos_service.service.projetos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntax.projetos_service.modelo.entidade.Projeto;
import com.syntax.projetos_service.repositorio.ProjetoRepositorio;

/**
 * Serviço para restaurar projetos da lixeira
 */
@Service
public class RestaurarProjetoService {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Restaura um projeto da lixeira
     *
     * @param dadosProjeto Dados do projeto em formato Map
     */
    public void restaurar(Object dadosProjeto) {
        try {
            // Converte os dados para a entidade Projeto
            Projeto projeto = objectMapper.convertValue(dadosProjeto, Projeto.class);

            if (projeto == null || projeto.getId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Dados do projeto inválidos"
                );
            }

            // Verifica se já existe
            if (projetoRepositorio.existsById(projeto.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Projeto já existe no banco de dados"
                );
            }

            // Salva o projeto restaurado
            projetoRepositorio.save(projeto);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Erro ao processar dados do projeto: " + e.getMessage()
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao restaurar projeto"
            );
        }
    }
}
