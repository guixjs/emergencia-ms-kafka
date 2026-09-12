package com.estudos.ms.emergencia.medicacao.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicacao_tb")
public class Medicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicacao;
    private String medicamento;
    private String dose;

    @Embedded
    private RelatorioTriagem relatorio;

    public Medicacao() {
    }

    public Medicacao(String medicamento, String dose, RelatorioTriagem relatorio) {
        this.medicamento = medicamento;
        this.dose = dose;
        this.relatorio = relatorio;
    }

    public Long getIdMedicacao() {
        return idMedicacao;
    }

    public void setIdMedicacao(Long idMedicacao) {
        this.idMedicacao = idMedicacao;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public RelatorioTriagem getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioTriagem relatorio) {
        this.relatorio = relatorio;
    }

}
