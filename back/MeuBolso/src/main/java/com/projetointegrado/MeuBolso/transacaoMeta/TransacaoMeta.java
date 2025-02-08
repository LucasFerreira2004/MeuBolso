package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.meta.Meta;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class TransacaoMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL) // Transação comum associada
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;

    @ManyToOne
    @JoinColumn(name = "meta_id", nullable = false)
    private Meta meta;

    public TransacaoMeta() {
    }

    public TransacaoMeta(Long id, Transacao transacao, Meta meta) {
        this.id = id;
        this.transacao = transacao;
        this.meta = meta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransacaoMeta that = (TransacaoMeta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
