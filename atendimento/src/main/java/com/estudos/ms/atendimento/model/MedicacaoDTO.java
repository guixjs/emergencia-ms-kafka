package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record MedicacaoDTO(
    String medicamento,
    String dose,
    RelatorioTriagem relatorio

) implements AtendimentoDTO {

  @Override
  public Encaminhamento encaminhamento() {
    return Encaminhamento.MEDICACAO;
  }
}
