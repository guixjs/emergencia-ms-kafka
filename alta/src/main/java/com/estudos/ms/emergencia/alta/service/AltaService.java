package com.estudos.ms.emergencia.alta.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.alta.model.Alta;
import com.estudos.ms.emergencia.alta.model.RelatorioTriagem;
import com.estudos.ms.emergencia.alta.repository.AltaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AltaService {

  private final AltaRepository altaRepository;
  private final ObjectMapper objectMapper;
  private final static Logger logger = LoggerFactory.getLogger(AltaService.class);
  private final KafkaTemplate<Long, String> kafkaTemplate;

  public AltaService(AltaRepository altaRepository, ObjectMapper objectMapper,
      KafkaTemplate<Long, String> kafkaTemplate) {
    this.altaRepository = altaRepository;
    this.objectMapper = objectMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void processarAlta(RelatorioTriagem relatorioTriagem) {
    var orientacao = "Repouso";
    var alta = new Alta(orientacao, relatorioTriagem);
    save(alta);
    liberarPaciente(alta);
  }

  public void liberarPaciente(Alta alta) {
    try {
      var json = objectMapper.writeValueAsString(alta);
      kafkaTemplate.send("PACIENTE_LIBERADO", json);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  private void save(Alta alta) {
    if (Objects.nonNull(alta)) {
      this.altaRepository.save(alta);
    } else {
      logger.error("Alta nula, não foi possível salvar.");
    }
  }

}
