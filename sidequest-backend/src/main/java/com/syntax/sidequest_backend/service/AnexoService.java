package com.syntax.sidequest_backend.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.syntax.sidequest_backend.modelo.dto.AnexoDTO;
import com.syntax.sidequest_backend.modelo.entidade.Anexo;
import com.syntax.sidequest_backend.repositorio.AnexoRepositorio;

@Service
public class AnexoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private AnexoRepositorio anexoRepositorio;

    public Anexo salvarAnexo(MultipartFile file, String tarefaId) throws IOException {
        // Salva o arquivo no GridFS
        ObjectId fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        // Cria o registro do anexo
        Anexo anexo = new Anexo();
        anexo.setTarefaId(tarefaId);
        anexo.setNomeOriginal(file.getOriginalFilename());
        anexo.setContentType(file.getContentType());
        anexo.setTamanho(file.getSize());
        anexo.setGridFsFileId(fileId.toString());
        anexo.setDataUpload(new Date());

        return anexoRepositorio.save(anexo);
    }

    public List<AnexoDTO> listarPorTarefa(String tarefaId) {
        List<Anexo> anexos = anexoRepositorio.findByTarefaId(tarefaId);
        return anexos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public GridFsResource buscarArquivo(String anexoId) {
        Anexo anexo = anexoRepositorio.findById(anexoId)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));

        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new Query(Criteria.where("_id").is(anexo.getGridFsFileId()))
        );

        if (gridFSFile == null) {
            throw new RuntimeException("Arquivo não encontrado no GridFS");
        }

        return gridFsTemplate.getResource(gridFSFile);
    }

    public void deletarAnexo(String anexoId) {
        Anexo anexo = anexoRepositorio.findById(anexoId)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));

        // Deleta do GridFS
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(anexo.getGridFsFileId())));

        // Deleta do repositório
        anexoRepositorio.deleteById(anexoId);
    }

    public void deletarPorTarefa(String tarefaId) {
        List<Anexo> anexos = anexoRepositorio.findByTarefaId(tarefaId);

        // Deleta cada arquivo do GridFS
        for (Anexo anexo : anexos) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(anexo.getGridFsFileId())));
        }

        // Deleta os registros
        anexoRepositorio.deleteByTarefaId(tarefaId);
    }

    private AnexoDTO converterParaDTO(Anexo anexo) {
        AnexoDTO dto = new AnexoDTO();
        dto.setId(anexo.getId());
        dto.setTarefaId(anexo.getTarefaId());
        dto.setNomeOriginal(anexo.getNomeOriginal());
        dto.setContentType(anexo.getContentType());
        dto.setTamanho(anexo.getTamanho());
        dto.setDataUpload(anexo.getDataUpload());
        dto.setUrlDownload("/api/anexos/" + anexo.getId() + "/download");
        return dto;
    }
}
