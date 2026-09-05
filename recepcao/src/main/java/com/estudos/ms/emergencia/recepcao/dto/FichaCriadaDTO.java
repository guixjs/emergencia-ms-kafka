package com.estudos.ms.emergencia.recepcao.dto;

import com.estudos.ms.emergencia.recepcao.model.Paciente;

public record FichaCriadaDTO(
    Long id,
    String sintomasRelatados,
    boolean isPreferencial,
    Paciente infoPaciente) {
}
