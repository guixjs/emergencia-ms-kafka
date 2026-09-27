package com.estudos.ms.emergencia.internacao.service;

import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.producer.ProducerRecord;
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
      var relatorio = internacao.getRelatorio();

      var json = objectMapper.writeValueAsString(internacao);
      var relatorioJson = objectMapper.writeValueAsString(relatorio);
      enviarMensagem("ENCAMINHAMENTO_ALTA", relatorioJson);
      enviarMensagem("INTERNACAO_FINALIZADA", json);
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
      enviarMensagem("INTERNACAO_INICIADA", json);
      System.out.println("Notificação Internação inciada: " + json);
    } catch (Exception e) {
      // TODO: log
    }
  }

  private void enviarMensagem(String topico, String mensagem) {
    var record = new ProducerRecord<Long, String>(topico, null, mensagem);
    record.headers().add("origem", "INTERNACAO".getBytes(StandardCharsets.UTF_8));
    kafkaTemplate.send(record);
  }

}
