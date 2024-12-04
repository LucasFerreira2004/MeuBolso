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
    private Date data_transacao;
    private Boolean e_fixo;
    private Integer qtd_repeticao;
    @Column(columnDefinition = "TEXT")
    private String comentario;
    @Column(columnDefinition = "TEXT")
    private String descricao;

    public Transacao(Long id, BigDecimal valor, Date data_transacao, Boolean e_fixo, Integer qtd_repeticao, String comentario, String descricao) {
        this.id = id;
        this.valor = valor;
        this.e_fixo = e_fixo;
        this.qtd_repeticao = qtd_repeticao;
        this.comentario = comentario;
        this.descricao = descricao;
        this.data_transacao = data_transacao;
    }
    public Transacao(Long id, BigDecimal valor, Date data_transacao, Boolean e_fixo, String descricao) {
        this.id = id;
        this.valor = valor;
        this.e_fixo = e_fixo;
        this.descricao = descricao;
        this.data_transacao = data_transacao;
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

    public Boolean getE_fixo() {
        return e_fixo;
    }

    public void setE_fixo(Boolean e_fixo) {
        this.e_fixo = e_fixo;
    }

    public Integer getQtd_repeticao() {
        return qtd_repeticao;
    }

    public void setQtd_repeticao(Integer qtd_repeticao) {
        this.qtd_repeticao = qtd_repeticao;
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
