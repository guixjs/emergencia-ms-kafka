package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public record InternacaoDTO(
        String quarto,
        String ala,
        String motivo,
        FichaCriadaDTO ficha
) implements AtendimentoDTO {

    @Override
    public Encaminhamento encaminhamento() {
        return Encaminhamento.INTERNACAO;
    }
}
