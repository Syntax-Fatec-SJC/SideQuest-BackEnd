package com.fatec.anexo_service.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.anexo_service.entidade.Anexo;
import com.fatec.anexo_service.entidade.dto.AnexoDTO;
import com.fatec.anexo_service.repositorio.AnexoRepository;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class AnexoService {

    private static final Logger logger = LoggerFactory.getLogger(AnexoService.class);

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * Salvar multiplos anexos RETORNA: List<AnexoDTO>
     */
    public List<AnexoDTO> salvarMultiplos(String tarefaId, List<MultipartFile> files) throws IOException {
        logger.info("Salvando {} anexos para tarefa: {}", files.size(), tarefaId);

        List<AnexoDTO> anexosSalvos = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                AnexoDTO dto = salvar(tarefaId, file);
                anexosSalvos.add(dto);
                logger.info("Anexo salvo: {} (ID: {})", dto.getNome(), dto.getId());
            } catch (Exception e) {
                logger.error("Erro ao salvar anexo: {}", file.getOriginalFilename(), e);
                throw e;
            }
        }

        return anexosSalvos;
    }

    /**
     * Salvar um anexo RETORNA: AnexoDTO
     */
    public AnexoDTO salvar(String tarefaId, MultipartFile file) throws IOException {
        logger.info("Salvando anexo: {} - Tamanho: {} bytes",
                file.getOriginalFilename(), file.getSize());

        ObjectId fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        logger.info("Arquivo salvo no GridFS com ID: {}", fileId);

        Anexo anexo = new Anexo();
        anexo.setTarefaId(tarefaId);
        anexo.setNome(file.getOriginalFilename());
        anexo.setContentType(file.getContentType());
        anexo.setTamanho(file.getSize());
        anexo.setGridFsFileId(fileId.toString());

        Anexo anexoSalvo = anexoRepository.save(anexo);

        logger.info("Anexo salvo na colecao: {}", anexoSalvo.getId());

        return new AnexoDTO(anexoSalvo);
    }

    /**
     * Listar anexos de uma tarefa RETORNA: List<AnexoDTO>
     */
    public List<AnexoDTO> listarPorTarefa(String tarefaId) {
        logger.info("Listando anexos da tarefa: {}", tarefaId);

        List<Anexo> anexos = anexoRepository.findByTarefaId(tarefaId);
        logger.info("Encontrados {} anexos", anexos.size());

        return anexos.stream()
                .map(AnexoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Buscar um anexo (modelo completo) RETORNA: Optional<Anexo>
     */
    public Optional<Anexo> buscarPorId(String id) {
        logger.info("Buscando anexo: {}", id);
        return anexoRepository.findById(id);
    }

    /**
     * Download arquivo (apenas bytes) RETORNA: byte[]
     */
    public byte[] downloadArquivo(String anexoId) throws IOException {
        logger.info("Download do anexo: {}", anexoId);

        Anexo anexo = anexoRepository.findById(anexoId)
                .orElseThrow(() -> new RuntimeException("Anexo nao encontrado: " + anexoId));

        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new Query(Criteria.where("_id").is(anexo.getGridFsFileId()))
        );

        if (gridFSFile == null) {
            throw new RuntimeException("Arquivo nao encontrado no GridFS: " + anexo.getGridFsFileId());
        }

        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);

        try (InputStream inputStream = resource.getInputStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            logger.info("Download concluido: {} bytes", outputStream.size());
            return outputStream.toByteArray();
        }
    }

    /**
     * Download com metadados (arquivo em Base64) RETORNA: AnexoDTO
     */
    public AnexoDTO downloadComMetadados(String anexoId) throws IOException {
        logger.info("Download com metadados: {}", anexoId);

        Anexo anexo = anexoRepository.findById(anexoId)
                .orElseThrow(() -> new RuntimeException("Anexo nao encontrado: " + anexoId));

        byte[] arquivoBytes = downloadArquivo(anexoId);
        String arquivoBase64 = Base64.getEncoder().encodeToString(arquivoBytes);

        return new AnexoDTO(anexo, arquivoBase64);
    }

    /**
     * Deletar um anexo
     */
    public void deletar(String anexoId) {
        logger.info("Deletando anexo: {}", anexoId);

        try {
            Anexo anexo = anexoRepository.findById(anexoId)
                    .orElseThrow(() -> new RuntimeException("Anexo nao encontrado: " + anexoId));

            if (anexo.getGridFsFileId() != null && !anexo.getGridFsFileId().isEmpty()) {
                try {
                    gridFsTemplate.delete(
                            new Query(Criteria.where("_id").is(anexo.getGridFsFileId()))
                    );
                    logger.info("Arquivo deletado do GridFS: {}", anexo.getGridFsFileId());
                } catch (Exception e) {
                    logger.error("Erro ao deletar arquivo do GridFS: {}", anexo.getGridFsFileId(), e);
                }
            }

            anexoRepository.deleteById(anexoId);
            logger.info("Documento do anexo deletado: {}", anexoId);

        } catch (Exception e) {
            logger.error("Erro ao deletar anexo: {}", anexoId, e);
            throw new RuntimeException("Erro ao deletar anexo: " + e.getMessage());
        }
    }

    /**
     * Deletar todos os anexos de uma tarefa
     */
    public void deletarPorTarefa(String tarefaId) {
        logger.info("===========================================");
        logger.info("Deletando TODOS os anexos da tarefa: {}", tarefaId);
        logger.info("===========================================");

        try {
            List<Anexo> anexos = anexoRepository.findByTarefaId(tarefaId);

            logger.info("Encontrados {} anexos para deletar", anexos.size());

            if (anexos.isEmpty()) {
                logger.info("Nenhum anexo encontrado para a tarefa: {}", tarefaId);
                return;
            }

            int deletados = 0;
            int erros = 0;

            for (Anexo anexo : anexos) {
                try {
                    deletar(anexo.getId());
                    deletados++;
                    logger.info("  Anexo deletado: {} (ID: {})", anexo.getNome(), anexo.getId());
                } catch (Exception e) {
                    erros++;
                    logger.error("  Erro ao deletar anexo: {} (ID: {})", anexo.getNome(), anexo.getId(), e);
                }
            }

            logger.info("===========================================");
            logger.info("Resultado: {} deletados, {} erros", deletados, erros);
            logger.info("===========================================");

        } catch (Exception e) {
            logger.error("Erro ao deletar anexos da tarefa: {}", tarefaId, e);
            throw new RuntimeException("Erro ao deletar anexos da tarefa: " + e.getMessage());
        }
    }
}
