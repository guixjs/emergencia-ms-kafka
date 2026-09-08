package com.estudos.ms.emergencia.alta.model;

import com.estudos.ms.emergencia.alta.enums.Risco;
import com.estudos.ms.emergencia.alta.enums.SetorEspecialidade;

public class RelatorioTriagem {
  private SetorEspecialidade setor;
  private Risco risco;
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
