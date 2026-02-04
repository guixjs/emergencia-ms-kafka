package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record AltaDTO(
        Long id,
        String orientacoes,
        FichaCriadaDTO ficha
) implements AtendimentoDTO{


    @Override
    public Encaminhamento encaminhamento() {
        return Encaminhamento.ALTA;
    }
}
