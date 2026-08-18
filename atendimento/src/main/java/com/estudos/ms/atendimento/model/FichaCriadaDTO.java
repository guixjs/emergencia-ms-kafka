package com.estudos.ms.atendimento.model;

public class FichaCriadaDTO {

    private Long idFicha;
    private String setor;
    private String risco;
    private String sintomasRelatados;
    private Boolean preferencial;
    private Paciente infoPaciente;

    public Long getIdFicha() {
        return idFicha;
    }

    public String getSetor() {
        return setor;
    }

    public String getRisco() {
        return risco;
    }

    public String getSintomasRelatados() {
        return sintomasRelatados;
    }

    public Boolean getPreferencial() {
        return preferencial;
    }

    public Paciente getInfoPaciente() {
        return infoPaciente;
    }
}
