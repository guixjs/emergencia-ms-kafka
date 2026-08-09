package com.estudos.ms.emergencia.alta.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.alta.model.Alta;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

  private final ObjectMapper objectMapper;
  private final ProcessarAlta processarAlta;

  public ConsumerService(ObjectMapper objectMapper, ProcessarAlta processarAlta) {
    this.objectMapper = objectMapper;
    this.processarAlta = processarAlta;
  }
  
  @KafkaListener(topics = "ATENDIMENTO_ALTA", groupId = "alta-group")
  public void consumirMensagemAlta(String mensagem) {
    try {
      var alta = objectMapper.readValue(mensagem, Alta.class);
      processarAlta.save(alta);
    } catch (Exception e) {
      System.err.println("Erro ao processar mensagem de alta: " + e.getMessage());
    }
  }
}
