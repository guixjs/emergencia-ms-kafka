package com.estudos.ms.atendimento.model;

public record FichaCriadaDTO(
        Long id,
        String setor,
        String risco,
        String sintomasRelatados,
        Boolean preferencial,
        Paciente infoPaciente
) {
}
