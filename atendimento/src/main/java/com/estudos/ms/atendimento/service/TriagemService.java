package com.estudos.ms.atendimento.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estudos.ms.atendimento.enums.Encaminhamento;
import com.estudos.ms.atendimento.enums.Risco;
import com.estudos.ms.atendimento.enums.SetorEspecialidade;
import com.estudos.ms.atendimento.model.Ficha;
import com.estudos.ms.atendimento.model.RelatorioTriagem;

@Service
public class TriagemService {

  private List<String> sintomasInternacao = List.of("Dor no peito", "Taquicardia", "Dor no estômago", "Fratura",
      "Acidente");

  private List<String> sintomasMedicacao = List.of("Febre", "Dor de cabeça", "Enjoo");

  public RelatorioTriagem gerarRelatorioMedico(Ficha ficha) {
    var isPreferencial = ficha.getPreferencial();
    var idadePaciente = ficha.getInfoPaciente().getIdade();
    var sintomas = ficha.getSintomasRelatados();

    var setor = verificarSetor(sintomas, idadePaciente);
    var risco = verificarRisco(sintomas, idadePaciente);
    var encaminhamento = verificarSituacao(isPreferencial, risco.toString());

    return new RelatorioTriagem(setor, risco, ficha, encaminhamento.toString());

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
    if (sintomasInternacao.contains(sintomas)) {
      return Risco.ALTO;
    }
    if (sintomasMedicacao.contains(sintomas)) {
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
      case "Taquicardia" -> SetorEspecialidade.CARDIOLOGIA;
      case "Fratura" -> SetorEspecialidade.ORTOPEDIA;
      case "Dor no estômago" -> SetorEspecialidade.CLINICO_GERAL;
      default -> SetorEspecialidade.CLINICO_GERAL;
    };
  }

}
