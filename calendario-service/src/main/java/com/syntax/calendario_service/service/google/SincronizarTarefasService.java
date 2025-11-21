package com.syntax.calendario_service.service.google;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import com.syntax.calendario_service.modelo.conversor.ConversorCredencialGoogle;
import com.syntax.calendario_service.modelo.conversor.ConversorEventoGoogle;
import com.syntax.calendario_service.modelo.dto.TarefaCalendarioDTO;
import com.syntax.calendario_service.modelo.entidade.GoogleToken;
import com.syntax.calendario_service.repositorio.GoogleTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SincronizarTarefasService {

    private static final Logger logger = LoggerFactory.getLogger(SincronizarTarefasService.class);

    @Autowired
    private HttpTransport httpTransport;
    @Autowired
    private JsonFactory jsonFactory;
    @Autowired
    private GoogleTokenRepository tokenRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ConversorCredencialGoogle conversorCredencial;
    @Autowired
    private ConversorEventoGoogle conversorEvento;

    @Value("${google.client-id}")
    private String clientId;
    @Value("${google.client-secret}")
    private String clientSecret;
    @Value("${tarefas.service.url}")
    private String tarefasServiceUrl;
    @Value("${gateway.secret}")
    private String gatewaySecret;

    public void execute(Long usuarioId) {
        GoogleToken token = tokenRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado com o Google."));

        try {
            UserCredentials userCredentials = conversorCredencial.paraCredencial(token, clientId, clientSecret);
            userCredentials.refresh();

            HttpCredentialsAdapter credential = new HttpCredentialsAdapter(userCredentials);

            Calendar calendarService = new Calendar.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("SideQuest").build();

            List<TarefaCalendarioDTO> tarefas = fetchTasksFromService(usuarioId);

            for (TarefaCalendarioDTO tarefa : tarefas) {
                Event event = conversorEvento.paraEntidade(tarefa);

                if (event != null) {
                    calendarService.events().insert("primary", event).execute();
                    logger.info("Evento '{}' criado no Google Calendar para o usuário {}", event.getSummary(), usuarioId);
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao sincronizar tarefas para o usuário {}: {}", usuarioId, e.getMessage(), e);
            throw new RuntimeException("Falha ao sincronizar com Google Calendar.", e);
        }
    }

    private List<TarefaCalendarioDTO> fetchTasksFromService(Long usuarioId) {
        String uri = tarefasServiceUrl + "/api/tarefas/usuario/" + usuarioId;
        logger.info("Buscando tarefas em: {}", uri);
        return webClientBuilder.build().get()
                .uri(uri)
                .header("X-Gateway-Secret", gatewaySecret)
                .header("X-User-Id", String.valueOf(usuarioId))
                .header("X-User-Email", "not-needed@example.com")
                .retrieve()
                .bodyToFlux(TarefaCalendarioDTO.class)
                .collectList()
                .block();
    }
}