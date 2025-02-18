package com.projetointegrado.MeuBolso.meta.notifications;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class MetaProgressEvent extends ApplicationEvent {
    private final String metaDescricao;
    private final BigDecimal progresso;
    private final int threshold;

    public MetaProgressEvent(String metaId, BigDecimal progresso, int threshold) {
        super(metaId);
        this.metaDescricao = metaId;
        this.progresso = progresso;
        this.threshold = threshold;
    }

    // Getters
    public String getMetaDescricao() { return metaDescricao; }
    public BigDecimal getProgresso() { return progresso; }
    public int getThreshold() { return threshold; }
}
