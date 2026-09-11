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

    public Alta() {
    }

    public Alta(String orientação, RelatorioTriagem relatorio) {
        this.orientação = orientação;
        this.relatorio = relatorio;
        this.dataHoraAltaPaciente = LocalDateTime.now();
    }

    public Long getIdIdAltaberacao() {
        return idAlta;
    }

    public void setIdAlta(Long id) {
        this.idAlta = id;
    }

    public String getOrientação() {
        return orientação;
    }

    public void setOrientação(String orientação) {
        this.orientação = orientação;
    }

    public LocalDateTime getDataHoraAltaPaciente() {
        return dataHoraAltaPaciente;
    }

    public void setDataHoraAltaPaciente(LocalDateTime dataHoraAltaPaciente) {
        this.dataHoraAltaPaciente = dataHoraAltaPaciente;
    }

}
