package com.estudos.ms.emergencia.internacao.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "internacao_tb")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInternacao;
    private String quarto;
    private String ala;
    private String motivo;

    @Embedded
    private RelatorioTriagem relatorio;

    private LocalDateTime dataHoraInicioInternacao;
    private LocalDateTime dataHoraFimInternacao;
    private boolean internacaoFinalizada;

    public Internacao() {
    }

    public Internacao(String quarto, String ala, String motivo, RelatorioTriagem relatorio) {
        this.quarto = quarto;
        this.ala = ala;
        this.motivo = motivo;
        this.relatorio = relatorio;
        this.dataHoraInicioInternacao = LocalDateTime.now();
        this.dataHoraFimInternacao = LocalDateTime.now().plusSeconds(30);
        this.internacaoFinalizada = false;
    }

    public Long getIdInternacao() {
        return idInternacao;
    }

    public void setIdInternacao(Long idInternacao) {
        this.idInternacao = idInternacao;
    }

    public String getQuarto() {
        return quarto;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public String getAla() {
        return ala;
    }

    public void setAla(String ala) {
        this.ala = ala;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public RelatorioTriagem getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioTriagem relatorio) {
        this.relatorio = relatorio;
    }

    public LocalDateTime getDataHoraInicioInternacao() {
        return dataHoraInicioInternacao;
    }

    public void setDataHoraInicioInternacao(LocalDateTime dataHoraInicioInternacao) {
        this.dataHoraInicioInternacao = dataHoraInicioInternacao;
    }

    public LocalDateTime getDataHoraFimInternacao() {
        return dataHoraFimInternacao;
    }

    public void setDataHoraFimInternacao(LocalDateTime dataHoraFimInternacao) {
        this.dataHoraFimInternacao = dataHoraFimInternacao;
    }

    public boolean isInternacaoFinalizada() {
        return internacaoFinalizada;
    }

    public void setInternacaoFinalizada(boolean internacaoFinalizada) {
        this.internacaoFinalizada = internacaoFinalizada;
    }

}
