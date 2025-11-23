package com.fatec.anexo_service.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.anexo_service.entidade.Anexo;
import com.fatec.anexo_service.entidade.dto.AnexoDTO;
import com.fatec.anexo_service.repositorio.AnexoRepository;
import com.fatec.anexo_service.service.AnexoService;

@RestController
@RequestMapping("/api/anexos")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // ========================================
    // ENDPOINT DE TESTE
    // ========================================
    @PostMapping("/test-save")
    public ResponseEntity<?> testSave() {
        try {
            System.out.println("========== TESTE DE SALVAMENTO ==========");

            Anexo teste = new Anexo();
            teste.setTarefaId("TEST-" + System.currentTimeMillis());
            teste.setNome("teste.txt");
            teste.setTipo("file");
            teste.setContentType("text/plain");
            teste.setTamanho(100L);
            teste.setArquivoBase64("VGVzdGU=");
            teste.setDataUpload(LocalDateTime.now());

            Anexo salvo = mongoTemplate.save(teste, "anexos");

            System.out.println("✅ SALVO! ID: " + salvo.getId());
            System.out.println("Total no banco: " + anexoRepository.count());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "OK");
            response.put("id", salvo.getId());
            response.put("totalNoBanco", anexoRepository.count());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("erro", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> listarTodos() {
        List<Anexo> todos = anexoRepository.findAll();
        System.out.println("Total no banco: " + todos.size());
        return ResponseEntity.ok(todos);
    }

    // ========================================
    // ENDPOINTS PRINCIPAIS
    // ========================================
    @PostMapping(value = "/upload/{tarefaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@PathVariable String tarefaId, @RequestPart("files") List<MultipartFile> files) {
        try {
            System.out.println("========== UPLOAD ==========");
            System.out.println("TarefaId: " + tarefaId);
            System.out.println("Arquivos: " + files.size());

            List<Anexo> salvos = anexoService.salvarMultiplos(tarefaId, files);

            System.out.println("✅ Salvos: " + salvos.size());
            System.out.println("Total no banco: " + anexoRepository.count());

            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", true);
            response.put("total", files.size());
            response.put("enviados", salvos.size());
            response.put("arquivos", salvos.stream().map(AnexoDTO::new).collect(Collectors.toList()));

            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("erro", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/{tarefaId}")
    public ResponseEntity<?> listar(@PathVariable String tarefaId) {
        try {
            System.out.println("========== LISTAR ==========");
            System.out.println("TarefaId: " + tarefaId);

            List<Anexo> anexos = anexoService.listarPorTarefa(tarefaId);

            System.out.println("Encontrados: " + anexos.size());

            List<Map<String, Object>> response = anexos.stream().map(a -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", a.getId());
                map.put("nome", a.getNome());
                map.put("tipo", a.getTipo());
                map.put("tamanho", a.getTamanhoFormatado());
                map.put("contentType", a.getContentType());
                map.put("dataUpload", a.getDataUpload() != null ? a.getDataUpload().toString() : null);
                return map;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("erro", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable String id) {
        try {
            Anexo anexo = anexoService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Não encontrado"));
            return ResponseEntity.ok(new AnexoDTO(anexo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        try {
            anexoService.deletar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Deletado", "id", id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/tarefa/{tarefaId}")
    public ResponseEntity<?> deletarTodos(@PathVariable String tarefaId) {
        try {
            anexoService.deletarPorTarefa(tarefaId);
            return ResponseEntity.ok(Map.of("mensagem", "Todos deletados"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "anexo-service");

        try {
            long total = anexoRepository.count();
            response.put("mongodb", "CONNECTED");
            response.put("totalAnexos", total);
        } catch (Exception e) {
            response.put("mongodb", "ERROR");
            response.put("mongodbError", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
