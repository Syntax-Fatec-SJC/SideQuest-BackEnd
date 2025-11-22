package com.syntax.avisos_service.controller.avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.syntax.avisos_service.modelo.dto.RespostaDTO.RespostaDTO;
import com.syntax.avisos_service.service.avisos.DeletarAvisoService;

/**
 * Controller para deletar aviso
 */
@RestController
public class DeletarAvisoController {

    @Autowired
    private DeletarAvisoService deletarAvisoService;

    @DeleteMapping("/avisos/{avisoId}")
    public ResponseEntity<RespostaDTO> deletar(@PathVariable String avisoId) {
        deletarAvisoService.executar(avisoId);
        return ResponseEntity.ok(new RespostaDTO("Aviso deletado com sucesso"));
    }
}
