package com.estudos.ms.emergencia.recepcao.dto;

public record NovaFichaRequestDTO(
        String nomePaciente,
        Integer idadePaciente,
        String sintomas
) {
}
