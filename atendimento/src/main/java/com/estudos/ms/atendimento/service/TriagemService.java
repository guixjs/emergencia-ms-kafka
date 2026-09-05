package com.estudos.ms.atendimento.service;

import org.springframework.stereotype.Service;

import com.estudos.ms.atendimento.enums.Encaminhamento;
import com.estudos.ms.atendimento.enums.Risco;
import com.estudos.ms.atendimento.enums.SetorEspecialidade;
import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import com.estudos.ms.atendimento.model.RelatorioTriagem;

@Service
public class TriagemService {

  public RelatorioTriagem gerarRelatorioMedico(FichaCriadaDTO ficha) {
    var isPreferencial = ficha.getPreferencial();
    var idadePaciente = ficha.getInfoPaciente().getIdade();
    var sintomas = ficha.getSintomasRelatados();

    var setor = verificarSetor(sintomas, idadePaciente);
    var risco = verificarRisco(sintomas, idadePaciente);
    var encaminhamento = verificarSituacao(isPreferencial, risco.toString());

    return new RelatorioTriagem(setor, risco, ficha, encaminhamento);

  }

  private Encaminhamento verificarSituacao(boolean isPreferencial, String risco) {

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

  private Risco verificarRisco(String sintomas, Integer idade) {
    if (sintomas.equalsIgnoreCase("Dor no peito")) {
      return Risco.ALTO;
    }
    if (sintomas.equalsIgnoreCase("Fratura")) {
      if (idade < 18 || idade > 65) {
        return Risco.ALTO;
      } else {
        return Risco.MEDIO;
      }
    } else {
      return Risco.BAIXO;
    }
  }

  private SetorEspecialidade verificarSetor(String sintomas, Integer idade) {
    if (idade < 18) {
      return SetorEspecialidade.PEDIATRIA;
    }
    return switch (sintomas) {
      case "Dor no peito" -> SetorEspecialidade.CARDIOLOGIA;
      case "Fratura" -> SetorEspecialidade.ORTOPEDIA;
      default -> SetorEspecialidade.CLINICO_GERAL;
    };
  }

}
