package com.projetointegrado.MeuBolso.Transacao;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    @Column(columnDefinition = "DATE")
    private Date data_transacao;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
    @Column(columnDefinition = "TEXT")
    private String comentario;
    @Column(columnDefinition = "TEXT")
    private String descricao;

    public Transacao(Long id, BigDecimal valor, Date data_transacao, TipoTransacao tipo, String comentario, String descricao) {
        this.id = id;
        this.valor = valor;
        this.data_transacao = data_transacao;
        this.tipo = tipo;
        this.comentario = comentario;
        this.descricao = descricao;
    }
    public Transacao(Long id, BigDecimal valor, Date data_transacao, Boolean e_fixo, String descricao) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.data_transacao = data_transacao;
        this.descricao = descricao;
    }
    public Transacao() {}

    // Getters e Setters
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData_transacao() {
        return data_transacao;
    }

    public void setData_transacao(Date data_transacao) {
        this.data_transacao = data_transacao;
    }
}
