package com.estudos.ms.emergencia.alta.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "alta_tb")
public class Alta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLiberacao;
    private String orientação;

    @Embedded
    private RelatorioTriagem ficha;

    public Alta() {
    }

    public Alta(String orientação, RelatorioTriagem ficha) {
        this.orientação = orientação;
        this.ficha = ficha;
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
