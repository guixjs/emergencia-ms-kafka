package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record AltaDTO(
        Long fichaId,
        String orientacoes
) implements AtendimentoDTO{


    @Override
    public Encaminhamento encaminhamento() {
        return Encaminhamento.ALTA;
    }
}
