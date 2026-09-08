package com.estudos.ms.atendimento.service;

import org.springframework.stereotype.Service;

import com.estudos.ms.atendimento.model.Ficha;

@Service
public class AtendimentoService {

    private final AtendimentoDispatcher atendimentoDispatcher;
    private final TriagemService triagemService;

    public AtendimentoService(AtendimentoDispatcher atendimentoDispatcher, TriagemService triagemService) {
        this.atendimentoDispatcher = atendimentoDispatcher;
        this.triagemService = triagemService;
    }

    public void atender(Ficha ficha) {
        atendimentoDispatcher.notificarAtendimentoInciado(ficha);

        var relatorio = triagemService.gerarRelatorioMedico(ficha);
        atendimentoDispatcher.encaminharPaciente(relatorio);

        atendimentoDispatcher.notificarAtendimentoConcluido(relatorio);
    }

}
