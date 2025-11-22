package com.syntax.tarefas_service.client;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.syntax.tarefas_service.modelo.dto.avisoDTO.AvisoDTO;

/**
 * Cliente para comunicação com o Avisos Service
 */
@Component
public class AvisosClient {

    private static final Logger logger = LoggerFactory.getLogger(AvisosClient.class);

    @Value("${avisos.service.url}")
    private String avisosServiceUrl;

    @Value("${gateway.secret}")
    private String gatewaySecret;

    private final RestTemplate restTemplate;

    public AvisosClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Cria um aviso no Avisos Service
     */
    public void criarAviso(AvisoDTO avisoDTO) {
        try {
            String url = avisosServiceUrl + "/cadastrar/avisos";
            logger.info("🔔 Criando aviso no Avisos Service: {}", avisoDTO.getMensagem());
            
            // Garante que a data está definida
            if (avisoDTO.getData() == null) {
                avisoDTO.setData(new Date());
            }
            
            // Garante que visualizado está false
            if (avisoDTO.getVisualizado() == null) {
                avisoDTO.setVisualizado(false);
            }
            
            // Adiciona header de autenticação entre microserviços
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Gateway-Secret", gatewaySecret);
            headers.set("Content-Type", "application/json");
            
            HttpEntity<AvisoDTO> request = new HttpEntity<>(avisoDTO, headers);
            restTemplate.postForObject(url, request, AvisoDTO.class);
            logger.info("✅ Aviso criado com sucesso");
        } catch (RestClientException e) {
            logger.warn("⚠️ Falha ao criar aviso no Avisos Service: {}", e.getMessage());
            // Não propaga erro para não afetar a operação principal
        }
    }

    /**
     * Cria aviso de nova tarefa criada
     */
    public void criarAvisoNovaTarefa(String tarefaId, String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("atribuicao");
        aviso.setMensagem(autorNome + " atrelou você a uma tarefa.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de membro adicionado à tarefa (com informações de quem foi adicionado)
     */
    public void criarAvisoMembroAdicionadoTarefa(String tarefaId, String projetoId, String usuarioId, String autorId, String autorNome, String membroAdicionadoId, String membroAdicionadoNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("atribuicao");
        aviso.setMensagem(autorNome + " atrelou " + membroAdicionadoNome + " a uma tarefa.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        aviso.setMembroAdicionadoId(membroAdicionadoId);
        aviso.setMembroAdicionadoNome(membroAdicionadoNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de tarefa editada
     */
    public void criarAvisoTarefaEditada(String tarefaId, String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("edicao");
        aviso.setMensagem(autorNome + " editou uma tarefa atrelada a você.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de tarefa atrasada
     */
    public void criarAvisoTarefaAtrasada(String tarefaId, String projetoId, String usuarioId) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("urgente");
        aviso.setMensagem("Uma tarefa atrelada a vocês está para vencer. Conclua ela o quanto antes!");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de anexo em tarefa
     */
    public void criarAvisoAnexoTarefa(String tarefaId, String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("edicao");
        aviso.setMensagem(autorNome + " anexou um arquivo em uma tarefa atrelada a você.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de tarefa excluída
     */
    public void criarAvisoTarefaExcluida(String tarefaId, String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("exclusao");
        aviso.setMensagem(autorNome + " excluiu uma tarefa atrelada a você.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de tarefa que vence hoje
     */
    public void criarAvisoTarefaVenceHoje(String tarefaId, String projetoId, String usuarioId) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("urgente");
        aviso.setMensagem("Atenção: Esta tarefa vence hoje");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de tarefa que passou do prazo
     */
    public void criarAvisoTarefaPassouPrazo(String tarefaId, String projetoId, String usuarioId) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("urgente");
        aviso.setMensagem("Lembrete: Esta tarefa passou do prazo");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setTarefaId(tarefaId);
        aviso.setProjetoId(projetoId);
        
        criarAviso(aviso);
    }
}
