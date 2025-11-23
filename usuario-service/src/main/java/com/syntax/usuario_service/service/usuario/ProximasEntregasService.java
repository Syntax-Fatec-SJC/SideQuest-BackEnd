package com.syntax.usuario_service.service.usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntax.usuario_service.modelo.dto.usuarioDTO.ProximasEntregasDTO;
import com.syntax.usuario_service.modelo.entidade.Usuario;

/**
 * Service para buscar próximas entregas do usuário
 */
@Service
public class ProximasEntregasService {

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    /**
     * Busca as próximas entregas de um usuário
     * 
     * @param usuarioId ID do usuário
     * @return Lista de próximas entregas
     */
    public List<ProximasEntregasDTO> buscarProximasEntregas(String usuarioId) {
        // Verifica se o usuário existe
        Usuario usuario = buscarUsuarioService.buscarPorId(usuarioId);
        
        // Por enquanto retorna lista vazia
        // TODO: Integrar com tarefas-service para buscar tarefas reais do usuário
        List<ProximasEntregasDTO> proximasEntregas = new ArrayList<>();
        
        // Exemplo de dados mockados para demonstração
        proximasEntregas.add(ProximasEntregasDTO.builder()
            .tarefaId("mock-tarefa-1")
            .titulo("Exemplo de Tarefa Próxima")
            .descricao("Esta é uma tarefa de exemplo")
            .projetoId("mock-projeto-1")
            .projetoTitulo("Projeto Exemplo")
            .dataEntrega(LocalDateTime.now().plusDays(7))
            .status("PENDENTE")
            .responsaveis(List.of(usuarioId))
            .build());
        
        return proximasEntregas;
    }
}
