package com.estudos.ms.emergencia.internacao.service;

import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import com.estudos.ms.emergencia.internacao.model.RelatorioTriagem;
import com.estudos.ms.emergencia.internacao.repository.InternacaoRepository;

@Service
public class InternacaoService {

  private final InternacaoRepository internacaoRepository;
  private final InternacaoDispatcherService internacaoDispatcher;

  public InternacaoService(InternacaoRepository internacaoRepository,
      InternacaoDispatcherService internacaoDispatcher) {
    this.internacaoRepository = internacaoRepository;
    this.internacaoDispatcher = internacaoDispatcher;
  }

  public void processarInternacao(RelatorioTriagem relatorio) {
    var quarto = "701";
    var ala = "Vermelha";
    var motivo = "Necessidade de procedimento cirúrgico";
    var internacao = new Internacao(quarto, ala, motivo, relatorio);
    save(internacao);
  }

  private void save(Internacao internacao) {
    try {
      internacaoRepository.save(internacao);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
