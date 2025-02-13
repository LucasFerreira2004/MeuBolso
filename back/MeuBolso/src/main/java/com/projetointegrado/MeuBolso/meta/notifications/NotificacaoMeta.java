package com.projetointegrado.MeuBolso.meta.notifications;

import com.projetointegrado.MeuBolso.meta.Meta;
import jakarta.persistence.*;

@Entity
public class NotificacaoMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer threshold;
    private Boolean notificado;

    @ManyToOne
    @JoinColumn(name = "meta_id")
    private Meta meta;

    public NotificacaoMeta() {
    }

    public NotificacaoMeta(Long id, Integer threshold, Boolean notificado, Meta meta) {
        this.id = id;
        this.threshold = threshold;
        this.notificado = notificado;
        this.meta = meta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}

