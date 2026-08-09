package com.estudos.ms.emergencia.alta.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.alta.model.Alta;
import com.estudos.ms.emergencia.alta.repository.AltaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessarAlta {

  private final AltaRepository altaRepository;
  private final ObjectMapper objectMapper;
  private final static Logger logger = LoggerFactory.getLogger(ProcessarAlta.class);

  public ProcessarAlta(AltaRepository altaRepository, ObjectMapper objectMapper) {
    this.altaRepository = altaRepository;
    this.objectMapper = objectMapper;
  }

  public void save(Alta alta) {
    if (Objects.nonNull(alta)) {
      var altaSalva = this.altaRepository.save(alta);
      try {
        var json = objectMapper.writeValueAsString(altaSalva);
        logger.info("Alta processada e salva com sucesso: " + json);
      } catch (Exception e) {
        logger.error("Erro ao converter alta para JSON: " + e.getMessage());
      }
    } else {
      logger.error("Alta nula, não foi possível salvar.");
    }
  }

}
