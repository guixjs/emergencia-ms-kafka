package com.estudos.ms.emergencia.recepcao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovaFichaRequestDTO(
        @NotBlank
        String nomePaciente,
        @NotNull
        Integer idadePaciente,
        @NotBlank
        String sintomas
) {
}
