package com.syntax.usuario_service.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.repositorio.UsuarioRepositorio;

/**
 * Service para deletar usuários
 */
@Service
@Transactional
public class DeletarUsuarioService {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    /**
     * Deleta um usuário por ID
     * 
     * @param id ID do usuário a ser deletado
     */
    public void deletar(String id) {
        // Verifica se o usuário existe antes de deletar
        Usuario usuario = buscarUsuarioService.buscarPorId(id);
        repositorio.delete(usuario);
    }
}
