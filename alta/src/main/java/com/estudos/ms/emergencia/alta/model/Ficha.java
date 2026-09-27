package com.estudos.ms.emergencia.alta.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Ficha {

    private Long idFicha;
    private String sintomasRelatados;
    private boolean preferencial;

    @Embedded
    private Paciente infoPaciente;

    public Long getIdFicha() {
        return idFicha;
    }

    public String getSintomasRelatados() {
        return sintomasRelatados;
    }

    public Boolean getPreferencial() {
        return preferencial;
    }

    public Paciente getInfoPaciente() {
        return infoPaciente;
    }
}
