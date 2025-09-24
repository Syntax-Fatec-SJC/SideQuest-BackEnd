package com.syntax.sidequest_backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.sidequest_backend.modelo.dto.MembroDTO;
import com.syntax.sidequest_backend.modelo.entidade.Membro;
import com.syntax.sidequest_backend.repositorio.MembroRepositorio;

@Service
public class MembroService {

    @Autowired
    private MembroRepositorio membroRepo;

    public Membro adicionarMembro(MembroDTO dto) {
        // Verificar email duplicado
        if (membroRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Membro membro = new Membro();
        membro.setNome(dto.getNome());
        membro.setEmail(dto.getEmail());
        membro.setFuncao(dto.getFuncao());
        return membroRepo.save(membro);
    }

    public List<Membro> listarMembros() {
        return membroRepo.findAll();
    }

    public Membro atualizarMembro(String membroId, MembroDTO dto) {
        Membro membro = membroRepo.findById(membroId)
            .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        // Verificar email duplicado ao atualizar
        membroRepo.findByEmail(dto.getEmail()).ifPresent(m -> {
            if (!m.getId().equals(membroId)) {
                throw new RuntimeException("Email já cadastrado");
            }
        });

        membro.setNome(dto.getNome());
        membro.setEmail(dto.getEmail());
        membro.setFuncao(dto.getFuncao());
        return membroRepo.save(membro);
    }

    public void removerMembro(String membroId) {
        membroRepo.deleteById(membroId);
    }
}
