package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record MedicacaoDTO(
        Long id,
        String medicamento,
        String dose,
        FichaCriadaDTO ficha
) implements AtendimentoDTO {

    @Override
    public Encaminhamento encaminhamento() {
        return Encaminhamento.MEDICACAO;
    }
}
