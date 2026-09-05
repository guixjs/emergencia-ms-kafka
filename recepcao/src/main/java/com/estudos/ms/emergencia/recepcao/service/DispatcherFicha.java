package com.estudos.ms.emergencia.recepcao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DispatcherFicha {

  private KafkaTemplate<Long, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final static String TOPICO = "FICHA_CRIADA";

  public DispatcherFicha(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void enviarFichaKafka(FichaCriadaDTO fichaCriada) {

    try {
      String json = objectMapper.writeValueAsString(fichaCriada);
      if (kafkaTemplate != null) {
        kafkaTemplate.send(TOPICO, fichaCriada.id(), json);
      }
    } catch (Exception e) {
      System.out.println("Erro:" + e.getMessage());
    }
  }

}
