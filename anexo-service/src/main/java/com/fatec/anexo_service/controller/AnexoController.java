package com.fatec.anexo_service.controller;

import java.util.*;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.anexo_service.entidade.Anexo;
import com.fatec.anexo_service.service.AnexoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/anexos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnexoController {

    private final AnexoService anexoService;

    @PostMapping("/{tarefaId}")
    public ResponseEntity<Map<String, Object>> upload(@PathVariable String tarefaId,
            @RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body(Map.of("sucesso", false, "mensagem", "Nenhum arquivo enviado"));
        }

        List<Map<String, Object>> enviados = new ArrayList<>();
        List<String> erros = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Anexo a = anexoService.salvarAnexo(file, tarefaId);
                Map<String, Object> info = new HashMap<>();
                info.put("nome", a.getFileName());
                info.put("tamanho", formatarTamanho(a.getFileSize()));
                info.put("tipo", a.getTipo());
                enviados.add(info);
            } catch (Exception e) {
                erros.add(file.getOriginalFilename() + ": " + e.getMessage());
            }
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("sucesso", erros.isEmpty());
        resposta.put("total", files.length);
        resposta.put("enviados", enviados.size());
        resposta.put("arquivos", enviados);
        if (!erros.isEmpty()) {
            resposta.put("erros", erros);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/{tarefaId}")
    public ResponseEntity<Map<String, Object>> listar(@PathVariable String tarefaId) {
        List<Anexo> anexos = anexoService.listarPorTarefa(tarefaId);
        List<Map<String, Object>> lista = new ArrayList<>();
        long totalBytes = 0;

        for (Anexo a : anexos) {
            Map<String, Object> info = new HashMap<>();
            info.put("nome", a.getFileName());
            info.put("tipo", a.getTipo());
            info.put("tamanho", formatarTamanho(a.getFileSize()));
            info.put("dataUpload", a.getDataUpload());
            lista.add(info);
            totalBytes += a.getFileSize();
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("tarefaId", tarefaId);
        resposta.put("totalArquivos", lista.size());
        resposta.put("tamanhoTotal", formatarTamanho(totalBytes));
        resposta.put("arquivos", lista);

        return ResponseEntity.ok(resposta);
    }

    private String formatarTamanho(Long bytes) {
        if (bytes == null || bytes == 0) {
            return "0 B";
        }
        double kb = bytes / 1024.0;
        if (kb < 1024) {
            return String.format("%.1f KB", kb);
        }
        double mb = kb / 1024.0;
        return String.format("%.1f MB", mb);
    }
}
