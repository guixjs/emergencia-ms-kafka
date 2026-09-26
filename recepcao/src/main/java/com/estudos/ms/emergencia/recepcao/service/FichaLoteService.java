package com.estudos.ms.emergencia.recepcao.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.model.Ficha;

@Service
public class FichaLoteService {

  private NovaFichaService novaFicha;
  private Random random;

  private List<String> sintomasInternacao = List.of("Dor no peito", "Taquicardia", "Dor no estômago", "Fratura",
      "Acidente");

  private List<String> sintomasMedicacao = List.of("Febre", "Dor de cabeça", "Enjoo");

  public FichaLoteService(NovaFichaService novaFicha) {
    this.novaFicha = novaFicha;
    this.random = new Random();
  }

  public void gerarFichaEmLote(Integer qtd, String encaminhamento) {

    List<String> lista;
    if (encaminhamento.equalsIgnoreCase("internacao")) {
      lista = sintomasInternacao;
    } else {
      lista = sintomasMedicacao;
    }

    for (int i = 0; i < qtd; i++) {
      var idade = random.nextInt(10, 80);
      var num = random.nextInt(0, 5);
      var sintoma = lista.get(num);
      var ficha = new NovaFichaRequestDTO("Paciente " + i, idade, sintoma);

      novaFicha.execute(ficha);
    }
  }

}
