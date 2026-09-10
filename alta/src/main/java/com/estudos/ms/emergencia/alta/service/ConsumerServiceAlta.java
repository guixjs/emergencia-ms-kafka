package com.estudos.ms.emergencia.alta.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.alta.model.RelatorioTriagem;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerServiceAlta {

  private final ObjectMapper objectMapper;
  private final AltaService altaService;

  public ConsumerServiceAlta(ObjectMapper objectMapper, AltaService altaService) {
    this.objectMapper = objectMapper;
    this.altaService = altaService;
  }

  @KafkaListener(topics = "ENCAMINHAMENTO_ALTA", groupId = "alta-group")
  public void consumirMensagemAlta(String mensagem) {
    try {
      var relatorio = objectMapper.readValue(mensagem, RelatorioTriagem.class);
      altaService.processarAlta(relatorio);
      System.out.println("Mensagem consumida!");
    } catch (Exception e) {
      System.err.println("Erro ao processar mensagem de alta: " + e.getMessage());
    }
  }
}
