package com.estudos.ms.emergencia.internacao.model;

public class Ficha {

    private Long id;
    private String setor;
    private String risco;
    private String sintomasRelatados;
    private Boolean preferencial;
    private Paciente infoPaciente;


    public Ficha(Long id, String setor, String risco, String sintomasRelatados, Boolean preferencial, Paciente infoPaciente) {
        this.id = id;
        this.setor = setor;
        this.risco = risco;
        this.sintomasRelatados = sintomasRelatados;
        this.preferencial = preferencial;
        this.infoPaciente = infoPaciente;
    }

    public Long getId() {
        return id;
    }

    public String getSetor() {
        return setor;
    }

    public String getRisco() {
        return risco;
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
