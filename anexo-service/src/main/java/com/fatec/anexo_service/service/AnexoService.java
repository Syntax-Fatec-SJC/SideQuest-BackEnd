package com.fatec.anexo_service.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.anexo_service.entidade.Anexo;
import com.fatec.anexo_service.entidade.Anexo.TipoAnexo;
import com.fatec.anexo_service.repositorio.AnexoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnexoService {

    private final AnexoRepository anexoRepository;

    private static final long MAX_FILE_SIZE = 200 * 1024 * 1024; // 200MB
    private static final List<String> TIPOS_PERMITIDOS = Arrays.asList(
            "image/png", "image/jpeg", "image/jpg", "application/pdf", "video/mp4"
    );

    @Transactional
    public Anexo salvarAnexo(MultipartFile file, String tarefaId) throws IOException {
        log.info("Upload do arquivo: {} para tarefa: {}", file.getOriginalFilename(), tarefaId);

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio não permitido");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Arquivo '" + file.getOriginalFilename() + "' excede 200MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !TIPOS_PERMITIDOS.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado: " + contentType);
        }

        TipoAnexo tipo = determinarTipoAnexo(contentType);

        Anexo anexo = new Anexo();
        anexo.setFileName(file.getOriginalFilename());
        anexo.setContentType(contentType);
        anexo.setFileSize(file.getSize());
        anexo.setTipo(tipo);
        anexo.setFileData(file.getBytes()); // streaming direto para MongoDB
        anexo.setTarefaId(tarefaId);
        anexo.setDataUpload(LocalDateTime.now());

        return anexoRepository.save(anexo);
    }

    public List<Anexo> listarPorTarefa(String tarefaId) {
        return anexoRepository.findByTarefaId(tarefaId);
    }

    public Anexo buscarPorId(String id) {
        return anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com ID: " + id));
    }

    @Transactional
    public void excluirAnexo(String id) {
        anexoRepository.delete(buscarPorId(id));
    }

    @Transactional
    public void excluirAnexosPorTarefa(String tarefaId) {
        anexoRepository.deleteByTarefaId(tarefaId);
    }

    public long contarAnexosPorTarefa(String tarefaId) {
        return anexoRepository.countByTarefaId(tarefaId);
    }

    private TipoAnexo determinarTipoAnexo(String contentType) {
        contentType = contentType.toLowerCase();
        if (contentType.startsWith("image/")) {
            return TipoAnexo.image;
        }
        if (contentType.equals("application/pdf")) {
            return TipoAnexo.pdf;
        }
        if (contentType.startsWith("video/")) {
            return TipoAnexo.video;
        }
        return null;
    }
}
