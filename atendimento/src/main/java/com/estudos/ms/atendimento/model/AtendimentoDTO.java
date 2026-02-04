package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public sealed interface AtendimentoDTO permits AltaDTO, InternacaoDTO, MedicacaoDTO {

    Long id();
    Encaminhamento encaminhamento();
    FichaCriadaDTO ficha();
}
