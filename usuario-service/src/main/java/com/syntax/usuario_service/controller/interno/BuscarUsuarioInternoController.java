package com.syntax.usuario_service.controller.interno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.usuario_service.modelo.dto.usuarioDTO.UsuarioDTO;
import com.syntax.usuario_service.service.usuario.BuscarUsuarioService;

/**
 * CONTROLLER INTERNO - BUSCAR USUÁRIO
 * 
 * Endpoint para comunicação entre microserviços.
 * Requer header X-Gateway-Secret mas não requer autenticação de usuário.
 * 
 * Endpoint: GET /internal/usuarios/{id}
 */
@RestController
@RequestMapping("/internal/usuarios")
public class BuscarUsuarioInternoController {

    @Autowired
    private BuscarUsuarioService buscarService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable String id) {
        try {
            var usuario = buscarService.buscarPorId(id);
            
            // Converter Usuario para UsuarioDTO
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            
            return ResponseEntity.ok(dto);
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro ao buscar usuário: " + e.getMessage());
        }
    }
}
