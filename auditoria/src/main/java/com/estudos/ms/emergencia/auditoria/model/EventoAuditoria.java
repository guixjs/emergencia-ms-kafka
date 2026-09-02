package com.estudos.ms.emergencia.auditoria.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventos")
public class EventoAuditoria {

  @Id
  private String id;
  private Long idFicha;
  private String topico;
  private Map<String, Object> mensagem;
  private LocalDateTime timestamp;

  public EventoAuditoria() {
  }

  public EventoAuditoria(Long idFicha, String topico, Map<String, Object> mensagem) {
    this.idFicha = idFicha;
    this.topico = topico;
    this.mensagem = mensagem;
    this.timestamp = LocalDateTime.now();
  }

  public void setIdFicha(Long idFicha) {
    this.idFicha = idFicha;
  }

  public void setTopico(String topico) {
    this.topico = topico;
  }

  public void setMensagem(Map<String, Object> mensagem) {
    this.mensagem = mensagem;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

}
