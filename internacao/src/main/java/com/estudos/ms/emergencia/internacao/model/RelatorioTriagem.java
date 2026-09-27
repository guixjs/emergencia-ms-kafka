package com.estudos.ms.emergencia.internacao.model;

import com.estudos.ms.emergencia.internacao.enums.Risco;
import com.estudos.ms.emergencia.internacao.enums.SetorEspecialidade;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class RelatorioTriagem {
  public RelatorioTriagem() {
  }

  @Enumerated(EnumType.STRING)
  private SetorEspecialidade setor;

  @Enumerated(EnumType.STRING)
  private Risco risco;

  @Embedded
  private Ficha ficha;
  private String encaminhamento;

  public RelatorioTriagem(SetorEspecialidade setor, Risco risco, Ficha ficha, String encaminhamento) {
    this.setor = setor;
    this.risco = risco;
    this.ficha = ficha;
    this.encaminhamento = encaminhamento;
  }

  public SetorEspecialidade getSetor() {
    return setor;
  }

  public Risco getRisco() {
    return risco;
  }

  public Ficha getFicha() {
    return ficha;
  }

  public String getEncaminhamento() {
    return encaminhamento;
  }

}
