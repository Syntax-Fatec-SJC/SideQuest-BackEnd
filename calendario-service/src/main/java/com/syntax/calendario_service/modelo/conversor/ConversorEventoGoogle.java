package com.syntax.calendario_service.modelo.conversor;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.syntax.calendario_service.modelo.dto.TarefaCalendarioDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConversorEventoGoogle {
    public Event paraEntidade(TarefaCalendarioDTO tarefaDTO) {
        String titulo = tarefaDTO.getTitulo();
        LocalDate dataEntrega = tarefaDTO.getDataEntrega();

        if (titulo == null || titulo.isBlank() || dataEntrega == null) {
            return null;
        }

        Event event = new Event().setSummary(titulo);

        com.google.api.client.util.DateTime startDateTime = new com.google.api.client.util.DateTime(java.sql.Date.valueOf(dataEntrega));
        event.setStart(new EventDateTime().setDate(startDateTime));

        com.google.api.client.util.DateTime endDateTime = new com.google.api.client.util.DateTime(java.sql.Date.valueOf(dataEntrega.plusDays(1)));
        event.setEnd(new EventDateTime().setDate(endDateTime));

        return event;
    }
}
