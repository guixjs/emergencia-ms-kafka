package com.estudos.ms.emergencia.alta.model;

import jakarta.persistence.*;

@Embeddable
public class Ficha {

    @Column(name = "ficha_id")
    private Long id;

    private String setor;
    private String risco;
    private String sintomasRelatados;
    private Boolean preferencial;
    @Embedded
    private Paciente infoPaciente;

    // Construtor padrão necessário para JPA
    public Ficha() {
    }

    public Ficha(Long id, String setor, String risco, String sintomasRelatados, Boolean preferencial,
            Paciente infoPaciente) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public String getSintomasRelatados() {
        return sintomasRelatados;
    }

    public void setSintomasRelatados(String sintomasRelatados) {
        this.sintomasRelatados = sintomasRelatados;
    }

    public Boolean getPreferencial() {
        return preferencial;
    }

    public void setPreferencial(Boolean preferencial) {
        this.preferencial = preferencial;
    }

    public Paciente getInfoPaciente() {
        return infoPaciente;
    }

    public void setInfoPaciente(Paciente infoPaciente) {
        this.infoPaciente = infoPaciente;
    }
}
