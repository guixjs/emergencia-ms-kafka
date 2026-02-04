package com.estudos.ms.emergencia.internacao.model;

public class Internacao {

    private String id;
    private String quarto;
    private String ala;
    private String motivo;

    private Ficha ficha;

    public String getId() {
        return id;
    }

    public String getQuarto() {
        return quarto;
    }

    public String getAla() {
        return ala;
    }

    public String getMotivo() {
        return motivo;
    }

    public Ficha getFicha() {
        return ficha;
    }

    @Override
    public String toString() {
        String dados = "";

        dados += "ID Internação: " + this.id + "\n";
        dados += "Quarto: " + this.quarto + "\n";
        dados += "Ala: " + this.ala + "\n";
        dados += "Motivo: " + this.motivo + "\n";
        dados += "---- Dados da Ficha ----\n";

        dados += "ID Ficha: " + this.ficha.getId() + "\n";
        dados += "Setor: " + this.ficha.getSetor() + "\n";
        dados += "Risco: " + this.ficha.getRisco() + "\n";
        dados += "Sintomas Relatados: " + this.ficha.getSintomasRelatados() + "\n";
        dados += "Preferencial: " + this.ficha.getPreferencial() + "\n";


        dados += "---- Dados do Paciente ----\n";
        dados += "Nome: " + this.ficha.getInfoPaciente().getNome() + "\n";
        dados += "Idade: " + this.ficha.getInfoPaciente().getIdade() + "\n";

        return dados;
    }
}
