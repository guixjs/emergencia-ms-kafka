package com.estudos.ms.emergencia.auditoria.service;

import java.time.LocalDateTime;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.auditoria.model.EventoAuditoria;
import com.estudos.ms.emergencia.auditoria.repository.AuditoriaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

  private final AuditoriaRepository repository;
  private final ObjectMapper objectMapper;

  public ConsumerService(AuditoriaRepository repository, ObjectMapper objectMapper) {
    this.repository = repository;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = {
      "ATENDIMENTO_ALTA",
      "ATENDIMENTO_MEDICACAO",
      "ATENDIMENTO_INTERNACAO" }, groupId = "auditoria-group")
  public void processarMensagem(ConsumerRecord<String, String> record) {
    EventoAuditoria evento = new EventoAuditoria();

    try {
      System.out.println("MENSAGEM CONSUMIDA: " + record.value() + " /topico: " + record.topic());
      JsonNode eventoJson = objectMapper.readTree(record.value());

      evento.setEvento(eventoJson);
      evento.setTopico(record.topic());
      evento.setTimestamp(LocalDateTime.now());

      repository.save(evento);
    } catch (Exception e) {
      System.out.println("Deu erro por algum motivo");
      e.printStackTrace();
    }

  }
}
