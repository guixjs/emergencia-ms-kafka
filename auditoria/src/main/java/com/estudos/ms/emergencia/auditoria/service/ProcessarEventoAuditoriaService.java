package com.estudos.ms.emergencia.auditoria.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estudos.ms.emergencia.auditoria.model.EventoAuditoria;
import com.estudos.ms.emergencia.auditoria.repository.AuditoriaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessarEventoAuditoriaService {

  private final ObjectMapper objectMapper;
  private final AuditoriaRepository auditoriaRepository;
  private final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

  public ProcessarEventoAuditoriaService(ObjectMapper objectMapper, AuditoriaRepository auditoriaRepository) {
    this.objectMapper = objectMapper;
    this.auditoriaRepository = auditoriaRepository;
  }

  public void salvarEvento(ConsumerRecord<String, String> record) {
    var topico = record.topic();
    JsonNode mensagemJson = converterMenagem(record.value());
    mensagemJson.get("idFicha");
    EventoAuditoria evento = new EventoAuditoria();

  }

  private JsonNode converterMenagem(String value) {
    try {
      return objectMapper.readTree(value);
    } catch (Exception e) {
      LOG.error("Erro ao converter mensagem");
      e.printStackTrace();
      return null;
    }
  }

}
