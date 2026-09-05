package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.enums.Encaminhamento;
import com.estudos.ms.atendimento.model.AltaDTO;
import com.estudos.ms.atendimento.model.AtendimentoDTO;
import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import com.estudos.ms.atendimento.model.InternacaoDTO;
import com.estudos.ms.atendimento.model.MedicacaoDTO;
import com.estudos.ms.atendimento.model.RelatorioTriagem;

import org.springframework.stereotype.Service;

@Service
public class AtendimentoService {

    private final AtendimentoDispatcher atendimentoDispatcher;
    private final TriagemService triagemService;

    public AtendimentoService(AtendimentoDispatcher atendimentoDispatcher, TriagemService triagemService) {
        this.atendimentoDispatcher = atendimentoDispatcher;
        this.triagemService = triagemService;
    }

    public void atender(FichaCriadaDTO ficha) {
        atendimentoDispatcher.notificarAtendimentoInciado(ficha);

        var relatorio = triagemService.gerarRelatorioMedico(ficha);
        atendimentoDispatcher.notificarAtendimentoConcluido(relatorio);

        var decisao = relatorio.getEncaminhamento();
        var atendimentoDTO = criarEncaminhamento(decisao, relatorio);

        atendimentoDispatcher.enviarAtendimento(atendimentoDTO,
                "ATENDIMENTO_" + atendimentoDTO.encaminhamento().name());
    }

    private AtendimentoDTO criarEncaminhamento(Encaminhamento decisao, RelatorioTriagem relatorio) {
        var atendimentoDTO = switch (decisao) {
            case INTERNACAO -> new InternacaoDTO("Quarto 101", "Ala A", "Cirurgia emergencial", relatorio);
            case MEDICACAO -> new MedicacaoDTO("Paracetamol", "500mg", relatorio);
            case ALTA -> new AltaDTO("Repouso", relatorio);
        };
        return atendimentoDTO;
    }

}
