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
  private String infoPaciente;
  private String topico;
  private Map<String, Object> mensagem;
  private LocalDateTime timestamp;

  public EventoAuditoria() {
  }

 

  public EventoAuditoria(Long idFicha, String infoPaciente, String topico, Map<String, Object> mensagem,
      LocalDateTime timestamp) {
    this.idFicha = idFicha;
    this.infoPaciente = infoPaciente;
    this.topico = topico;
    this.mensagem = mensagem;
    this.timestamp = timestamp;
  }



  public void setIdFicha(Long idFicha) {
    this.idFicha = idFicha;
  }

  public void setInfoPaciente(String infoPaciente) {
    this.infoPaciente = infoPaciente;
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
