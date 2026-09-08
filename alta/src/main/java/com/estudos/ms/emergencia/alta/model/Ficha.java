package com.estudos.ms.emergencia.alta.model;

public class Ficha {

    private Long idFicha;
    private String sintomasRelatados;
    private boolean preferencial;
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
