package com.estudos.ms.atendimento.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.atendimento.model.Ficha;
import com.estudos.ms.atendimento.model.RelatorioTriagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AtendimentoDispatcher {

  private final KafkaTemplate<Long, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final Logger LOGGER = LoggerFactory.getLogger(AtendimentoDispatcher.class);

  public AtendimentoDispatcher(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void encaminharPaciente(RelatorioTriagem relatorioTriagem) {
    try {
      var json = objectMapper.writeValueAsString(relatorioTriagem);
      var topico = relatorioTriagem.getEncaminhamento().toString();
      topico = "ENCAMINHAMENTO_" + topico;
      enviarMensagem(topico, json);
    } catch (JsonProcessingException e) {
      LOGGER.error("Erro ao converter mensagem " + e.getMessage());
    }
  }

  public void notificarAtendimentoInciado(Ficha ficha) {
    try {
      var json = objectMapper.writeValueAsString(ficha);
      enviarMensagem("ATENDIMENTO_INCIADO", json);
    } catch (JsonProcessingException e) {
      LOGGER.error("Erro ao converter mensagem " + e.getMessage());
    }
  }

  public void notificarAtendimentoConcluido(RelatorioTriagem relatorio) {
    try {
      var json = objectMapper.writeValueAsString(relatorio);
      enviarMensagem("ATENDIMENTO_CONCLUIDO", json);
    } catch (JsonProcessingException e) {
      LOGGER.error("Erro ao converter mensagem " + e.getMessage());
    }
  }

  private void enviarMensagem(String topico, String mensagem) {
    try {
      if (Objects.nonNull(topico)) {
        kafkaTemplate.send(topico, mensagem);
        System.out.println("Mensagem enviada! " + topico + " - " + mensagem);
      } else {
        LOGGER.error("Topico nao informado!");

      }
    } catch (Exception e) {
      LOGGER.error("Nao foi possivel enviar a mensagem: " + e.getMessage());
    }
  }
}
