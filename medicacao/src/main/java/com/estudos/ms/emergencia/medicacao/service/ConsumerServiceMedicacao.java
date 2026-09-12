package com.estudos.ms.emergencia.medicacao.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.medicacao.model.RelatorioTriagem;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerServiceMedicacao {

  private final ObjectMapper objectMapper;
  private final MedicacaoService processarMedicacao;

  public ConsumerServiceMedicacao(ObjectMapper objectMapper, MedicacaoService processarMedicacao) {
    this.objectMapper = objectMapper;
    this.processarMedicacao = processarMedicacao;
  }

  @KafkaListener(topics = "ENCAMINHAMENTO_MEDICACAO", groupId = "medicacao-group")
  public void consumirMensagemMedicacao(String mensagem) {
    try {
      var relatorio = objectMapper.readValue(mensagem, RelatorioTriagem.class);
      processarMedicacao.processarMedicacao(relatorio);
      System.out.println("Mensagem consumida!");
    } catch (Exception e) {
      System.err.println("Erro: " + e.getMessage());
    }
  }
}