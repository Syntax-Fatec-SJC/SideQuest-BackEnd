package com.syntax.usuario_service.controller.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.usuario_service.modelo.dto.usuarioDTO.ProximasEntregasDTO;
import com.syntax.usuario_service.service.usuario.ProximasEntregasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller para buscar próximas entregas do usuário
 */
@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class ProximasEntregasController {

    @Autowired
    private ProximasEntregasService proximasEntregasService;

    @Operation(
        summary = "Buscar próximas entregas",
        description = "Retorna as tarefas com entregas próximas de um usuário"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de entregas retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}/proximas-entregas")
    public ResponseEntity<List<ProximasEntregasDTO>> buscarProximasEntregas(@PathVariable String id) {
        List<ProximasEntregasDTO> entregas = proximasEntregasService.buscarProximasEntregas(id);
        return ResponseEntity.ok(entregas);
    }
}
