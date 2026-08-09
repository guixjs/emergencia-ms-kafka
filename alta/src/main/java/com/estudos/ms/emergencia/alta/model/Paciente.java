package com.estudos.ms.emergencia.alta.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Paciente {
    private String nome;
    private Integer idade;

    public Paciente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
