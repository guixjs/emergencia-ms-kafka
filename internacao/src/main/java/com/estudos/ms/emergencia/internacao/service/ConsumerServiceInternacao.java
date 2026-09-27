package com.estudos.ms.emergencia.internacao.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.internacao.model.RelatorioTriagem;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerServiceInternacao {

  private final ObjectMapper objectMapper;
  private final InternacaoService internacaoService;

  public ConsumerServiceInternacao(ObjectMapper objectMapper, InternacaoService internacaoService) {
    this.internacaoService = internacaoService;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "ENCAMINHAMENTO_INTERNACAO", groupId = "internacao-group")
  public void consumirMensagemAlta(String mensagem) {
    try {
      var relatorio = objectMapper.readValue(mensagem, RelatorioTriagem.class);
      internacaoService.processarInternacao(relatorio);
      System.out.println("Mensagem consumida!");
    } catch (Exception e) {
      System.err.println("Erro: " + e.getMessage());
    }
  }
}
