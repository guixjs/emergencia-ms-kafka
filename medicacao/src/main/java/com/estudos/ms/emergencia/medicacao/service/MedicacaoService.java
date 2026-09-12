package com.estudos.ms.emergencia.medicacao.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.medicacao.model.Medicacao;
import com.estudos.ms.emergencia.medicacao.model.RelatorioTriagem;
import com.estudos.ms.emergencia.medicacao.repository.MedicacaoRepository;

@Service
public class MedicacaoService {

  private final MedicacaoRepository medicacaoRepository;
  private final MedicacaoDispatcherService medicacaoDispatcher;

  public MedicacaoService(MedicacaoRepository medicacaoRepository, MedicacaoDispatcherService medicacaoDispatcher) {
    this.medicacaoRepository = medicacaoRepository;
    this.medicacaoDispatcher = medicacaoDispatcher;
  }

  public void processarMedicacao(RelatorioTriagem relatorio) {
    // TODO Log
    var dose = "5mg";
    var medicamento = "Dipirona";
    var medicacao = new Medicacao(medicamento, dose, relatorio);
    var medicacaoSalva = save(medicacao);
    medicacaoDispatcher.notificarMedicacaoIniciada(medicacaoSalva);
    liberarPaciente(medicacao);
  }

  private void liberarPaciente(Medicacao medicacao) {
    try {
      // simulando tempo da medicação
      Thread.sleep(1000);

      medicacaoDispatcher.notificarLiberacaoPaciente(medicacao);
    } catch (InterruptedException e) {
      // TODO log
      e.printStackTrace();
    }
  }

  public Medicacao save(Medicacao medicacao) {
    try {
      return medicacaoRepository.save(medicacao);
    } catch (Exception e) {
      // TODO: logs
    }
    return null;
  }

}
