package com.syntax.usuario_service.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syntax.usuario_service.modelo.dto.usuarioDTO.AtualizarUsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.repositorio.UsuarioRepositorio;

/**
 * Service para atualização de dados do usuário
 */
@Transactional
@Service
public class AtualizarUsuarioService {

    @Autowired
    private UsuarioRepositorio repositorio;

    /**
     * Atualiza os dados de um usuário
     * 
     * @param usuario Entidade do usuário a ser atualizado
     * @param dto DTO com os novos dados
     * @return Usuário atualizado
     */
    public Usuario atualizarUsuario(Usuario usuario, AtualizarUsuarioDTO dto) {
        usuario.setNome(dto.getNome());
        return repositorio.save(usuario);
    }
}
