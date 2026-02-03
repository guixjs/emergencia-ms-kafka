package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record MedicacaoDTO(
        Long fichaId,
        String medicamento,
        String dose
) implements AtendimentoDTO {

    @Override
    public Encaminhamento encaminhamento() {
        return Encaminhamento.MEDICACAO;
    }
}
