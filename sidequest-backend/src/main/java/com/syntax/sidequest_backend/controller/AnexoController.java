package com.syntax.sidequest_backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.syntax.sidequest_backend.modelo.dto.AnexoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Anexo;
import com.syntax.sidequest_backend.service.AnexoService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/anexos")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    // Upload de múltiplos arquivos para uma tarefa
    @PostMapping("/tarefa/{tarefaId}")
    public ResponseEntity<List<AnexoDTO>> uploadAnexos(
            @PathVariable String tarefaId,
            @RequestParam("files") List<MultipartFile> files) {

        try {
            List<AnexoDTO> anexosSalvos = new ArrayList<>();

            for (MultipartFile file : files) {
                Anexo anexo = anexoService.salvarAnexo(file, tarefaId);

                AnexoDTO dto = new AnexoDTO();
                dto.setId(anexo.getId());
                dto.setTarefaId(anexo.getTarefaId());
                dto.setNomeOriginal(anexo.getNomeOriginal());
                dto.setContentType(anexo.getContentType());
                dto.setTamanho(anexo.getTamanho());
                dto.setDataUpload(anexo.getDataUpload());
                dto.setUrlDownload("/api/anexos/" + anexo.getId() + "/download");

                // ✅ NOVOS: Retorna dados da tarefa
                dto.setStatusTarefa(anexo.getStatusTarefa());
                dto.setNomeTarefa(anexo.getNomeTarefa());
                dto.setDescricaoTarefa(anexo.getDescricaoTarefa());
                dto.setComentarioTarefa(anexo.getComentarioTarefa());

                anexosSalvos.add(dto);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(anexosSalvos);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar anexos de uma tarefa
    @GetMapping("/tarefa/{tarefaId}")
    public ResponseEntity<List<AnexoDTO>> listarAnexos(@PathVariable String tarefaId) {
        List<AnexoDTO> anexos = anexoService.listarPorTarefa(tarefaId);
        return ResponseEntity.ok(anexos);
    }

    // Download de um anexo específico
    @GetMapping("/{anexoId}/download")
    public ResponseEntity<Resource> downloadAnexo(@PathVariable String anexoId) {
        try {
            GridFsResource resource = anexoService.buscarArquivo(anexoId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(resource.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar um anexo
    @DeleteMapping("/{anexoId}")
    public ResponseEntity<Void> deletarAnexo(@PathVariable String anexoId) {
        try {
            anexoService.deletarAnexo(anexoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar todos anexos de uma tarefa
    @DeleteMapping("/tarefa/{tarefaId}")
    public ResponseEntity<Void> deletarAnexosDaTarefa(@PathVariable String tarefaId) {
        anexoService.deletarPorTarefa(tarefaId);
        return ResponseEntity.noContent().build();
    }
}
