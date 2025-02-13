package com.projetointegrado.MeuBolso.orcamento.notifications;

import com.projetointegrado.MeuBolso.orcamento.Orcamento;
import jakarta.persistence.*;

@Entity
public class NotificacaoOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer threshold;
    private Boolean notificado;

    @ManyToOne
    @JoinColumn(name = "orcamento_id")
    private Orcamento orcamento;

    public NotificacaoOrcamento() {
    }

    public NotificacaoOrcamento(Integer threshold, Orcamento orcamento) {
        this.threshold = threshold;
        this.orcamento = orcamento;
        this.notificado = false;
    }

    public Long getId() {
        return id;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Boolean isNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
}

