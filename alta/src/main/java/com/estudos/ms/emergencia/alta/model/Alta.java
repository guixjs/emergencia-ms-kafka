package com.estudos.ms.emergencia.alta.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alta_tb")
public class Alta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlta;
    private String orientação;

    @Embedded
    private RelatorioTriagem relatorio;

    private LocalDateTime dataHoraAltaPaciente;
    private String origem;

    public Alta() {
    }

    public Alta(String orientação, RelatorioTriagem relatorio, String origem) {
        this.orientação = orientação;
        this.relatorio = relatorio;
        this.origem = origem;
        this.dataHoraAltaPaciente = LocalDateTime.now();
    }

    public Long getIdAlta() {
        return idAlta;
    }

    public void setIdAlta(Long idAlta) {
        this.idAlta = idAlta;
    }

    public String getOrientação() {
        return orientação;
    }

    public void setOrientação(String orientação) {
        this.orientação = orientação;
    }

    public RelatorioTriagem getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioTriagem relatorio) {
        this.relatorio = relatorio;
    }

    public LocalDateTime getDataHoraAltaPaciente() {
        return dataHoraAltaPaciente;
    }

    public void setDataHoraAltaPaciente(LocalDateTime dataHoraAltaPaciente) {
        this.dataHoraAltaPaciente = dataHoraAltaPaciente;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

}
