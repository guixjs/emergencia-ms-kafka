package com.estudos.ms.emergencia.recepcao.model;

import com.estudos.ms.emergencia.recepcao.enums.Risco;
import com.estudos.ms.emergencia.recepcao.enums.SetorEspecialidade;

public class Ficha {

    private Long idFicha;
    private Paciente paciente;
    private SetorEspecialidade setor;
    private Risco risco;
    private String sintomas;
    private boolean preferencial;

    public Ficha(Paciente paciente, SetorEspecialidade setor, Risco risco, String sintomas, boolean preferencial) {
        this.paciente = paciente;
        this.setor = setor;
        this.risco = risco;
        this.sintomas = sintomas;
        this.preferencial = preferencial;
    }
}
