package com.estudos.ms.emergencia.recepcao.dto;

import com.estudos.ms.emergencia.recepcao.model.Paciente;

public record FichaCriadaDTO(
        Long id,
        String setor,
        String risco,
        String sintomasRelatados,
        Boolean preferencial,
        Paciente infoPaciente
) {
}
