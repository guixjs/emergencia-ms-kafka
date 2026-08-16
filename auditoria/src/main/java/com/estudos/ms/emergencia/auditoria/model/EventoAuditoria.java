package com.estudos.ms.emergencia.auditoria.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.JsonNode;

@Document(collection = "eventos")
public class EventoAuditoria {

  @Id
  private String id;
  private String infoPaciente;
  private String topico;
  private JsonNode evento;
  private LocalDateTime timestamp;

  public EventoAuditoria() {
  }

  public EventoAuditoria(String id, String infoPaciente, String topico, JsonNode evento) {
    this.id = id;
    this.infoPaciente = infoPaciente;
    this.topico = topico;
    this.evento = evento;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setInfoPaciente(String infoPaciente) {
    this.infoPaciente = infoPaciente;
  }

  public void setTopico(String topico) {
    this.topico = topico;
  }

  public void setEvento(JsonNode evento) {
    this.evento = evento;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

}
