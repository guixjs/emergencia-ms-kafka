package com.estudos.ms.emergencia.recepcao.mapper;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.estudos.ms.emergencia.recepcao.model.Ficha;
import com.estudos.ms.emergencia.recepcao.model.Paciente;

public class FichaMapper {
    public static FichaCriadaDTO converteDeEntidadeParaRespostaDTO(Ficha ficha) {

        var paciente = new Paciente(ficha.getPaciente().getNome(), ficha.getPaciente().getIdade());

        return new FichaCriadaDTO(ficha.getId(), ficha.getSintomas(), ficha.isPreferencial(), paciente);
    }
}
