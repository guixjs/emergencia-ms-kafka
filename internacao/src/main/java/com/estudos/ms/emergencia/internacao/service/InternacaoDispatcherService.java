package com.estudos.ms.emergencia.internacao.service;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InternacaoDispatcherService {

  private ObjectMapper objectMapper;
  private KafkaTemplate<Long, String> kafkaTemplate;

  public InternacaoDispatcherService(ObjectMapper objectMapper, KafkaTemplate<Long, String> kafkaTemplate) {
    this.objectMapper = objectMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void liberarPaciente(Internacao internacao) {
    try {
      var json = objectMapper.writeValueAsString(internacao);
      kafkaTemplate.send("PACIENTE_LIBERADO", json);
      kafkaTemplate.send("INTERNACAO_FINALIZADA", json);
      System.out
          .println("Paciente liberado e internação finalizada" + json + " data e hora now: " + LocalDateTime.now());
    } catch (Exception e) {
      // TODO: log
      e.printStackTrace();
    }
  }

  public void notificarInternacao(Internacao internacao) {
    try {
      var json = objectMapper.writeValueAsString(internacao);
      kafkaTemplate.send("INTERNACAO_INICIADA", json);
      System.out.println("Notificação Internação inciada: " + json);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

}
