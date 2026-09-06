package com.estudos.ms.emergencia.alta.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.alta.model.Alta;
import com.estudos.ms.emergencia.alta.repository.AltaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessarAlta {

  private final AltaRepository altaRepository;
  private final ObjectMapper objectMapper;
  private final static Logger logger = LoggerFactory.getLogger(ProcessarAlta.class);
  private final KafkaTemplate<Long, String> kafkaTemplate;

  public ProcessarAlta(AltaRepository altaRepository, ObjectMapper objectMapper,
      KafkaTemplate<Long, String> kafkaTemplate) {
    this.altaRepository = altaRepository;
    this.objectMapper = objectMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void execute(Alta alta) {
    try {
      var json = objectMapper.writeValueAsString(alta);
      kafkaTemplate.send("PACIENTE_LIBERADO", json);
      save(alta);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public void save(Alta alta) {
    if (Objects.nonNull(alta)) {
      this.altaRepository.save(alta);
    } else {
      logger.error("Alta nula, não foi possível salvar.");
    }
  }

}
