package com.estudos.ms.emergencia.alta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alta_tb")
public class Alta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orientação;

    @Embedded
    private Ficha ficha;

    public Alta() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrientação() {
        return orientação;
    }

    public void setOrientação(String orientação) {
        this.orientação = orientação;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

}
