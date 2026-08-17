package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.enums.Encaminhamento;
import com.estudos.ms.atendimento.model.AltaDTO;
import com.estudos.ms.atendimento.model.AtendimentoDTO;
import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import com.estudos.ms.atendimento.model.InternacaoDTO;
import com.estudos.ms.atendimento.model.MedicacaoDTO;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoService {


    private final AtendimentoDispatcher atendimentoDispatcher;

    public AtendimentoService(AtendimentoDispatcher atendimentoDispatcher) {
        this.atendimentoDispatcher = atendimentoDispatcher;
    }

    public void atender(FichaCriadaDTO ficha) {
        var decisao = verificarSituacao(ficha);
        var atendimentoDTO = criarEncaminhamento(decisao, ficha);
        atendimentoDispatcher.enviarAtendimento(atendimentoDTO, "ATENDIMENTO_"+ atendimentoDTO.encaminhamento().name());
    }

    private AtendimentoDTO criarEncaminhamento(Encaminhamento decisao, FichaCriadaDTO ficha) {
        var atendimentoDTO = switch (decisao) {
            case INTERNACAO -> new InternacaoDTO("Quarto 101", "Ala A", "Cirurgia emergencial", ficha);
            case MEDICACAO -> new MedicacaoDTO("Paracetamol", "500mg", ficha);
            case ALTA -> new AltaDTO("Repouso",ficha);
        };
        return atendimentoDTO;
    }

    private Encaminhamento verificarSituacao(FichaCriadaDTO ficha) {
        var isPreferencial = ficha.getPreferencial();
        var risco = ficha.getRisco();

        if (isPreferencial) {
            return Encaminhamento.INTERNACAO;
        }

        if (risco.equalsIgnoreCase("ALTO")) {
            return Encaminhamento.INTERNACAO;
        }

        if (risco.equalsIgnoreCase("MEDIO")) {
            return Encaminhamento.MEDICACAO;
        }
        return Encaminhamento.ALTA;
    }
}
