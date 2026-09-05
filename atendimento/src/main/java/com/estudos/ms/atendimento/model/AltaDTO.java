package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record AltaDTO(
    String orientacoes,
    RelatorioTriagem relatorio) implements AtendimentoDTO {

  @Override
  public Encaminhamento encaminhamento() {
    return Encaminhamento.ALTA;
  }

}
