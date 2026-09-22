package com.estudos.ms.emergencia.auditoria.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventos")
public class EventoAuditoria {

  @Id
  private String id;
  private String idEvento;
  private Long idFicha;
  private String origem;
  private String destino;
  private String tipoEvento;
  private Map<String, Object> payload;
  private LocalDateTime timestamp;

  public EventoAuditoria() {
  }

  public EventoAuditoria(String idEvento, Long idFicha, String origem, String destino,
      String tipoEvento, Map<String, Object> payload, LocalDateTime timestamp) {
    this.idEvento = idEvento;
    this.idFicha = idFicha;
    this.origem = origem;
    this.destino = destino;
    this.tipoEvento = tipoEvento;
    this.payload = payload;
    this.timestamp = timestamp;
  }

  public String getEventId() {
    return idEvento;
  }

  public Long getIdFicha() {
    return idFicha;
  }

  public String getOrigem() {
    return origem;
  }

  public String getDestino() {
    return destino;
  }

  public String getTipoEvento() {
    return tipoEvento;
  }

  public Map<String, Object> getPayload() {
    return payload;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

}
