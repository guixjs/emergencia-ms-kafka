package com.estudos.ms.emergencia.medicacao.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.medicacao.model.Medicacao;
import com.estudos.ms.emergencia.medicacao.repository.MedicacaoRepository;

@Service
public class ProcessarMedicacao {

  private final MedicacaoRepository medicacaoRepository;

  public ProcessarMedicacao(MedicacaoRepository medicacaoRepository) {
    this.medicacaoRepository = medicacaoRepository;
  }

  public void save(Medicacao medicacao) {
    if (Objects.nonNull(medicacao))
      this.medicacaoRepository.save(medicacao);
  }
}
