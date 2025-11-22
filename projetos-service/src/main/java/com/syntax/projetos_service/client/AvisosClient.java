package com.syntax.projetos_service.client;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.syntax.projetos_service.modelo.dto.avisoDTO.AvisoDTO;

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
            
            if (avisoDTO.getData() == null) {
                avisoDTO.setData(new Date());
            }
            
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
        }
    }

    /**
     * Cria aviso de projeto atualizado
     */
    public void criarAvisoProjetoAtualizado(String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("edicao");
        aviso.setMensagem(autorNome + " atualizou um projeto atrelado a você.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de novo membro adicionado ao projeto
     */
    public void criarAvisoNovoMembroProjeto(String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("atribuicao");
        aviso.setMensagem(autorNome + " atrelou você a um projeto.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de membro adicionado ao projeto (com informações de quem foi adicionado)
     */
    public void criarAvisoMembroAdicionadoProjeto(String projetoId, String usuarioId, String autorId, String autorNome, String membroAdicionadoId, String membroAdicionadoNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("atribuicao");
        aviso.setMensagem(autorNome + " atrelou " + membroAdicionadoNome + " a um projeto.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        aviso.setMembroAdicionadoId(membroAdicionadoId);
        aviso.setMembroAdicionadoNome(membroAdicionadoNome);
        
        criarAviso(aviso);
    }

    /**
     * Cria aviso de projeto excluído
     */
    public void criarAvisoProjetoExcluido(String projetoId, String usuarioId, String autorId, String autorNome) {
        AvisoDTO aviso = new AvisoDTO();
        aviso.setTipo("exclusao");
        aviso.setMensagem(autorNome + " excluiu um projeto atrelado a você.");
        aviso.setData(new Date());
        aviso.setVisualizado(false);
        aviso.setUsuarioId(usuarioId);
        aviso.setProjetoId(projetoId);
        aviso.setAutorId(autorId);
        aviso.setAutorNome(autorNome);
        
        criarAviso(aviso);
    }
}
