package com.estudos.ms.emergencia.recepcao.model;

import com.estudos.ms.emergencia.recepcao.enums.Risco;
import com.estudos.ms.emergencia.recepcao.enums.SetorEspecialidade;
import jakarta.persistence.*;


@Entity
@Table(name = "fichas")
public class Ficha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    private SetorEspecialidade setor;

    @Enumerated(EnumType.STRING)
    private Risco risco;

    private String sintomas;
    
    private boolean preferencial;


    public Ficha() {
    }

    public Ficha(Paciente paciente, SetorEspecialidade setor, Risco risco, String sintomas, boolean preferencial) {
        this.paciente = paciente;
        this.setor = setor;
        this.risco = risco;
        this.sintomas = sintomas;
        this.preferencial = preferencial;
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getSetor() {
        return setor.toString();
    }

    public String getRisco() {
        return risco.toString();
    }

    public String getSintomas() {
        return sintomas;
    }

    public boolean isPreferencial() {
        return preferencial;
    }
}
