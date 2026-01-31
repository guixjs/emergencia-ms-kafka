package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record InternacaoDTO(
        Long fichaId,
        String quarto,
        String ala
) implements AtendimentoDTO {

    @Override
    public Encaminhamento encaminhamento() {
        return Encaminhamento.INTERNACAO;
    }
}
