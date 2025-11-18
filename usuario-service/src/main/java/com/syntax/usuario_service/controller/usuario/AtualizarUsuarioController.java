package com.syntax.usuario_service.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.usuario_service.modelo.conversor.ConversorUsuario;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.AtualizarUsuarioDTO;
import com.syntax.usuario_service.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;
import com.syntax.usuario_service.service.usuario.AtualizarUsuarioService;
import com.syntax.usuario_service.service.usuario.BuscarUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller para atualização de dados do usuário
 */
@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class AtualizarUsuarioController {

    @Autowired
    private AtualizarUsuarioService servicoAtualizarUsuario;

    @Autowired
    private BuscarUsuarioService servicoBuscarUsuario;

    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados de um usuário específico por ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
        @PathVariable String id, 
        @Valid @RequestBody AtualizarUsuarioDTO dto
    ) {
        Usuario usuario = servicoBuscarUsuario.buscarPorId(id);
        Usuario usuarioAtualizado = servicoAtualizarUsuario.atualizarUsuario(usuario, dto);
        UsuarioDTO resposta = ConversorUsuario.converter(usuarioAtualizado);
        
        return ResponseEntity.ok(resposta);
    }
}
