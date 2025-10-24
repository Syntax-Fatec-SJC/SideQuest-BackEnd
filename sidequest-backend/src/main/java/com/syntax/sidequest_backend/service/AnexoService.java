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
import com.syntax.sidequest_backend.modelo.entidade.Tarefa;
import com.syntax.sidequest_backend.repositorio.AnexoRepositorio;
import com.syntax.sidequest_backend.repositorio.TarefaRepositorio;

@Service
public class AnexoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private AnexoRepositorio anexoRepositorio;

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    public Anexo salvarAnexo(MultipartFile file, String tarefaId) throws IOException {
        // ✅ Busca a tarefa para pegar TODOS os dados
        Tarefa tarefa = tarefaRepositorio.findById(tarefaId).orElse(null);

        String statusTarefa = "Desconhecido";
        String nomeTarefa = "Sem nome";
        String descricaoTarefa = "";
        String comentarioTarefa = "";

        if (tarefa != null) {
            statusTarefa = tarefa.getStatus() != null ? tarefa.getStatus() : "Desconhecido";
            nomeTarefa = tarefa.getNome() != null ? tarefa.getNome() : "Sem nome";
            descricaoTarefa = tarefa.getDescricao() != null ? tarefa.getDescricao() : "";
            comentarioTarefa = tarefa.getComentario() != null ? tarefa.getComentario() : "";
        }

        // Salva o arquivo no GridFS
        ObjectId fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        // Cria o registro do anexo com TODOS os dados da tarefa
        Anexo anexo = new Anexo();
        anexo.setTarefaId(tarefaId);
        anexo.setNomeOriginal(file.getOriginalFilename());
        anexo.setContentType(file.getContentType());
        anexo.setTamanho(file.getSize());
        anexo.setGridFsFileId(fileId.toString());
        anexo.setDataUpload(new Date());

        // ✅ NOVOS: Salva dados da tarefa
        anexo.setStatusTarefa(statusTarefa);
        anexo.setNomeTarefa(nomeTarefa);
        anexo.setDescricaoTarefa(descricaoTarefa);
        anexo.setComentarioTarefa(comentarioTarefa);

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

        // ✅ NOVOS: Retorna dados da tarefa
        dto.setStatusTarefa(anexo.getStatusTarefa());
        dto.setNomeTarefa(anexo.getNomeTarefa());
        dto.setDescricaoTarefa(anexo.getDescricaoTarefa());
        dto.setComentarioTarefa(anexo.getComentarioTarefa());

        return dto;
    }
}
