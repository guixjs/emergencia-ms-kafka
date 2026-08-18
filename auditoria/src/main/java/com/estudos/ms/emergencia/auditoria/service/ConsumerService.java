package com.estudos.ms.emergencia.auditoria.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

  private final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);
  private final ProcessarEventoAuditoriaService processarAuditoria;

  public ConsumerService(ProcessarEventoAuditoriaService processarAuditoria) {
    this.processarAuditoria = processarAuditoria;
  }

  @KafkaListener(topics = {
      "FICHA_CRIADA",
      "ATENDIMENTO_ALTA",
      "ATENDIMENTO_MEDICACAO",
      "ATENDIMENTO_INTERNACAO",
      "PACIENTE_INTERNADO",
      "PACIENTE_MEDICADO",
      "PACIENTE_LIBERADO" }, groupId = "auditoria-group")
  public void processarMensagem(ConsumerRecord<String, String> record) {
    try {
      LOG.info("Evento capturado no topico: {}", record.topic());

      System.out.println("Chegou aqui");
      processarAuditoria.salvarEvento(record);
    } catch (Exception e) {
      System.out.println("Deu erro por algum motivo");
      e.printStackTrace();
    }

  }
}
