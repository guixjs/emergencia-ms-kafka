package com.estudos.ms.atendimento.model;

public class FichaCriadaDTO {

    private Long id;
    private String sintomasRelatados;
    private boolean preferencial;
    private Paciente infoPaciente;

    public Long getId() {
        return id;
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
