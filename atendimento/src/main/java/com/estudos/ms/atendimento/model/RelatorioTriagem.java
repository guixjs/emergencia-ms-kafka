package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;
import com.estudos.ms.atendimento.enums.Risco;
import com.estudos.ms.atendimento.enums.SetorEspecialidade;

public class RelatorioTriagem {
  private SetorEspecialidade setor;
  private Risco risco;
  private FichaCriadaDTO ficha;
  private Encaminhamento encaminhamento;

  public RelatorioTriagem(SetorEspecialidade setor, Risco risco, FichaCriadaDTO ficha, Encaminhamento encaminhamento) {
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

  public FichaCriadaDTO getFicha() {
    return ficha;
  }

  public Encaminhamento getEncaminhamento() {
    return encaminhamento;
  }

}
