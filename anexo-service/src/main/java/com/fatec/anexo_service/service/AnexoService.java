package com.fatec.anexo_service.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.anexo_service.entidade.Anexo;
import com.fatec.anexo_service.repositorio.AnexoRepository;

@Service
public class AnexoService {

    @Autowired
    private AnexoRepository anexoRepository;

    /**
     * Salvar um novo anexo (single file logic)
     */
    public Anexo salvar(String tarefaId, MultipartFile file) throws IOException {
        // Determinar o tipo do arquivo
        String tipo = determinarTipo(file.getContentType());

        // Converter arquivo para Base64
        String arquivoBase64 = Base64.getEncoder().encodeToString(file.getBytes());

        Anexo anexo = new Anexo(
                tarefaId,
                file.getOriginalFilename(),
                tipo,
                file.getContentType(),
                file.getSize(),
                arquivoBase64
        );

        // Salvar no MongoDB
        return anexoRepository.save(anexo);
    }

    /**
     * NOVO MÉTODO: Salvar múltiplos anexos
     */
    public List<Anexo> salvarMultiplos(String tarefaId, List<MultipartFile> files) throws IOException {
        List<Anexo> anexosSalvos = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // Chama a lógica de salvamento de arquivo único
                anexosSalvos.add(salvar(tarefaId, file));
            }
        }
        return anexosSalvos;
    }

    /**
     * Listar anexos de uma tarefa
     */
    public List<Anexo> listarPorTarefa(String tarefaId) {
        return anexoRepository.findByTarefaId(tarefaId);
    }

    /**
     * Buscar anexo por ID
     */
    public Optional<Anexo> buscarPorId(String id) {
        return anexoRepository.findById(id);
    }

    /**
     * Deletar anexo por ID
     */
    public void deletar(String id) {
        anexoRepository.deleteById(id);
    }

    /**
     * Deletar todos os anexos de uma tarefa
     */
    public void deletarPorTarefa(String tarefaId) {
        anexoRepository.deleteByTarefaId(tarefaId);
    }

    /**
     * Determinar tipo do arquivo baseado no contentType
     */
    private String determinarTipo(String contentType) {
        if (contentType == null) {
            return "file";
        }

        if (contentType.startsWith("image/")) {
            return "image";
        }

        if (contentType.equals("application/pdf")) {
            return "pdf";
        }

        if (contentType.startsWith("video/")) {
            return "video";
        }

        return "file";
    }
}
