package com.estudos.ms.atendimento.model;

import com.estudos.ms.atendimento.enums.Encaminhamento;

public interface AtendimentoDTO {
    Encaminhamento encaminhamento();
    RelatorioTriagem relatorio();
}
