package com.estudos.ms.emergencia.medicacao.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.medicacao.model.Medicacao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

  private final ObjectMapper objectMapper;
  private final ProcessarMedicacao processarMedicacao;

  public ConsumerService(ObjectMapper objectMapper, ProcessarMedicacao processarMedicacao) {
    this.objectMapper = objectMapper;
    this.processarMedicacao = processarMedicacao;
  }

  @KafkaListener(topics = "ATENDIMENTO_MEDICACAO", groupId = "medicacao-group")
  public void consumirMensagemMedicacao(String mensagem) {
    try {
      var medicacao = objectMapper.readValue(mensagem, Medicacao.class);

    } catch (Exception e) {
      System.err.println("Erro ao processar mensagem de medicação: " + e.getMessage());
    }
  }
}