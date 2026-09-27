package com.estudos.ms.emergencia.medicacao.service;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.medicacao.model.Medicacao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MedicacaoDispatcherService {

  private final ObjectMapper objectMapper;
  private final KafkaTemplate<Long, String> kafkaTemplate;

  public MedicacaoDispatcherService(ObjectMapper objectMapper, KafkaTemplate<Long, String> kafkaTemplate) {
    this.objectMapper = objectMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void notificarMedicacaoIniciada(Medicacao medicacao) {
    try {
      var json = objectMapper.writeValueAsString(medicacao);
      enviarMensagem("MEDICACAO_INICIADA", json);
      System.out.println("Notificação Internação inciada: " + json);
    } catch (Exception e) {
      // TODO: log
    }
  }

  private void enviarMensagem(String topico, String mensagem) {
    var record = new ProducerRecord<Long, String>(topico, null, mensagem);
    record.headers().add("origem", "MEDICACAO".getBytes(StandardCharsets.UTF_8));
    kafkaTemplate.send(record);
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public void notificarLiberacaoPaciente(Medicacao medicacao) {
    // TODO log
    try {
      var json = objectMapper.writeValueAsString(medicacao);
      enviarMensagem("MEDICACAO_CONCLUIDA", json);

      var relatorio = medicacao.getRelatorio();
      var relatorioJson = objectMapper.writeValueAsString(relatorio);
      enviarMensagem("ENCAMINHAMENTO_ALTA", relatorioJson);
      System.out.println("Notificação Internação finalizada: " + json);
    } catch (Exception e) {
      // TODO: log
    }
  }

}
