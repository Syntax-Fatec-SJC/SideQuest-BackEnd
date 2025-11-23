package com.syntax.tarefas_service.service.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.syntax.tarefas_service.repositorio.TarefaRepositorio;

/**
 * Service para deletar tarefa E seus anexos
 */
@Service
public class DeletarTarefaService {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    // URL do anexo-service (pode vir do application.properties se preferir)
    private static final String ANEXO_SERVICE_URL = "http://localhost:8087/api/anexos";

    // RestTemplate para chamar o anexo-service
    private final RestTemplate restTemplate = new RestTemplate();

    public void executar(String id) {
        // 1. Verificar se tarefa existe
        if (!tarefaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }

        // 2. DELETAR ANEXOS PRIMEIRO (chamada ao anexo-service)
        try {
            System.out.println("[DeletarTarefaService] Deletando anexos da tarefa: " + id);

            String url = ANEXO_SERVICE_URL + "/tarefa/" + id;
            restTemplate.delete(url);

            System.out.println("[DeletarTarefaService]  Anexos deletados com sucesso!");

        } catch (Exception e) {
            // Se der erro ao deletar anexos, loga mas NÃO impede de deletar a tarefa
            System.err.println("[DeletarTarefaService]  Erro ao deletar anexos: " + e.getMessage());
            // Continua mesmo assim...
        }

        // 3. Deletar a tarefa
        System.out.println("[DeletarTarefaService] Deletando tarefa: " + id);
        tarefaRepositorio.deleteById(id);
        System.out.println("[DeletarTarefaService]  Tarefa deletada com sucesso!");
    }
}
