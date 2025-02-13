package com.projetointegrado.MeuBolso.orcamento.notifications;

public class NotificacaoOrcamentoDTO {
    private int threshold;
    private boolean notificado;

    public NotificacaoOrcamentoDTO(int threshold, boolean notificado) {
        this.threshold = threshold;
        this.notificado = notificado;
    }

    public NotificacaoOrcamentoDTO(NotificacaoOrcamento notificacao) {
        this.threshold = notificacao.getThreshold();
        this.notificado = true;
    }

    public NotificacaoOrcamentoDTO(NotificacaoOrcamentoDTO notificacaoOrcamentoDTO) {
    }

    // Getters e Setters
    public int getThreshold() { return threshold; }
    public void setThreshold(int threshold) { this.threshold = threshold; }

    public boolean isNotificado() { return notificado; }
    public void setNotificado(boolean notificado) { this.notificado = notificado; }
}

