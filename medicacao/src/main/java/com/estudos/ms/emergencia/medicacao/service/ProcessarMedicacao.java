package com.estudos.ms.emergencia.medicacao.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.medicacao.model.Medicacao;
import com.estudos.ms.emergencia.medicacao.repository.MedicacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessarMedicacao {

  private final MedicacaoRepository medicacaoRepository;
  private final ObjectMapper objectMapper;
  private final static Logger logger = LoggerFactory.getLogger(ProcessarMedicacao.class);

  public ProcessarMedicacao(MedicacaoRepository medicacaoRepository, ObjectMapper objectMapper) {
    this.medicacaoRepository = medicacaoRepository;
    this.objectMapper = objectMapper;
  }

  public void save(Medicacao medicacao) {
    if (Objects.nonNull(medicacao)) { 
      var medicacaoSalva = this.medicacaoRepository.save(medicacao);
      try {
        var json = objectMapper.writeValueAsString(medicacaoSalva);
        logger.info("Medicacao processada e salva com sucesso: " + json);
      } catch (Exception e) {
        logger.error("Erro ao converter medicação para JSON: " + e.getMessage());
      }
    }else{
      logger.error("Medicação nula, não foi possível salvar.");
    }
  }
}
