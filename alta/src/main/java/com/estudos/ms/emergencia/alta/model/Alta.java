package com.estudos.ms.emergencia.alta.model;

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
    private Long idLiberacao;
    private String orientação;

    @Embedded
    private RelatorioTriagem relatorio;

    public Alta() {
    }

    public Alta(String orientação, RelatorioTriagem relatorio) {
        this.orientação = orientação;
        this.relatorio = relatorio;
    }

    public Long getIdLiberacao() {
        return idLiberacao;
    }

    public void setId(Long id) {
        this.idLiberacao = id;
    }

    public String getOrientação() {
        return orientação;
    }

    public void setOrientação(String orientação) {
        this.orientação = orientação;
    }
}
