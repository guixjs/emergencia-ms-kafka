package com.estudos.ms.atendimento.model;

public class Paciente {
    private String nome;
    private Integer idade;


    public Paciente() {
    }

    public Paciente(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }
}
