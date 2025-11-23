package com.fatec.anexo_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.anexo_service.entidade.Anexo;
import com.fatec.anexo_service.entidade.dto.AnexoDTO;
import com.fatec.anexo_service.service.AnexoService;

@RestController
@RequestMapping("/api/anexos")
public class AnexoController {

    private static final Logger logger = LoggerFactory.getLogger(AnexoController.class);

    @Autowired
    private AnexoService anexoService;

    /**
     * Upload de multiplos anexos
     */
    @PostMapping(value = "/upload/{tarefaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @PathVariable String tarefaId,
            @RequestParam("files") List<MultipartFile> files) {

        logger.info("===========================================");
        logger.info("Upload de anexos - TarefaId: {}", tarefaId);
        logger.info("Arquivos: {}", files.size());
        logger.info("===========================================");

        try {
            // Service retorna List<AnexoDTO>
            List<AnexoDTO> anexosSalvos = anexoService.salvarMultiplos(tarefaId, files);

            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", true);
            response.put("total", files.size());
            response.put("enviados", anexosSalvos.size());
            response.put("arquivos", anexosSalvos);

            logger.info("Upload concluido: {} arquivos salvos", anexosSalvos.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Erro no upload", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("sucesso", false);
            errorResponse.put("erro", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Listar anexos de uma tarefa
     */
    @GetMapping("/{tarefaId}")
    public ResponseEntity<List<AnexoDTO>> listar(@PathVariable String tarefaId) {
        logger.info("Listando anexos da tarefa: {}", tarefaId);

        try {
            // Service retorna List<AnexoDTO>
            List<AnexoDTO> anexos = anexoService.listarPorTarefa(tarefaId);
            return ResponseEntity.ok(anexos);

        } catch (Exception e) {
            logger.error("Erro ao listar anexos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Download de um anexo (arquivo binario)
     */
    @GetMapping("/download/{anexoId}")
    public ResponseEntity<?> download(@PathVariable String anexoId) {
        logger.info("Download do anexo: {}", anexoId);

        try {
            // Buscar metadados
            Anexo anexo = anexoService.buscarPorId(anexoId)
                    .orElseThrow(() -> new RuntimeException("Anexo nao encontrado"));

            // Baixar arquivo
            byte[] arquivoBytes = anexoService.downloadArquivo(anexoId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(anexo.getContentType()));
            headers.setContentDispositionFormData("attachment", anexo.getNome());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(arquivoBytes);

        } catch (Exception e) {
            logger.error("Erro no download", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Download com metadados (arquivo em Base64)
     */
    @GetMapping("/download-base64/{anexoId}")
    public ResponseEntity<?> downloadBase64(@PathVariable String anexoId) {
        logger.info("Download Base64 do anexo: {}", anexoId);

        try {
            // Service retorna AnexoDTO com arquivo Base64
            AnexoDTO anexoDTO = anexoService.downloadComMetadados(anexoId);
            return ResponseEntity.ok(anexoDTO);

        } catch (Exception e) {
            logger.error("Erro no download Base64", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Deletar um anexo
     */
    @DeleteMapping("/{anexoId}")
    public ResponseEntity<?> deletar(@PathVariable String anexoId) {
        logger.info("Deletando anexo: {}", anexoId);

        try {
            anexoService.deletar(anexoId);

            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", true);
            response.put("mensagem", "Anexo deletado com sucesso");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Erro ao deletar anexo", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("sucesso", false);
            errorResponse.put("erro", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Deletar todos os anexos de uma tarefa
     */
    @DeleteMapping("/tarefa/{tarefaId}")
    public ResponseEntity<?> deletarPorTarefa(@PathVariable String tarefaId) {
        logger.info("===========================================");
        logger.info("Deletando todos os anexos da tarefa: {}", tarefaId);
        logger.info("===========================================");

        try {
            anexoService.deletarPorTarefa(tarefaId);

            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", true);
            response.put("mensagem", "Todos os anexos da tarefa foram deletados");

            logger.info("Todos os anexos deletados com sucesso!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Erro ao deletar anexos da tarefa", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("sucesso", false);
            errorResponse.put("erro", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "anexo-service");
        response.put("mongodb", "CONNECTED");

        return ResponseEntity.ok(response);
    }
}
