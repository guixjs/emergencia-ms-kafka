package com.estudos.ms.emergencia.recepcao.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fichas")
public class Ficha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Paciente paciente;

    private String sintomas;

    private boolean preferencial;

    public Ficha() {
    }

    public Ficha(Paciente paciente, String sintomas, boolean preferencial) {
        this.paciente = paciente;
        this.sintomas = sintomas;
        this.preferencial = preferencial;
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getSintomas() {
        return sintomas;
    }

    public boolean isPreferencial() {
        return preferencial;
    }
}
