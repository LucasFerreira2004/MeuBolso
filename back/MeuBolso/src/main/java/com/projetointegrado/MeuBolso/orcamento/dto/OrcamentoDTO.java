package com.projetointegrado.MeuBolso.orcamento.dto;

import com.projetointegrado.MeuBolso.categoria.Categoria;

import java.math.BigDecimal;

public class OrcamentoDTO {
    private Long id;
    private String descricao;
    private BigDecimal valorEstimado;
    private BigDecimal valorGasto;
    private BigDecimal valorRestante;
    private Categoria categoria;

    public OrcamentoDTO() {
    }

    public OrcamentoDTO(Long id, String descricao, BigDecimal valorEstimado, BigDecimal valorGasto, BigDecimal valorRestante, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.valorGasto = valorGasto;
        this.valorRestante = valorRestante;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public BigDecimal getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(BigDecimal valorGasto) {
        this.valorGasto = valorGasto;
    }

    public BigDecimal getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(BigDecimal valorRestante) {
        this.valorRestante = valorRestante;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.descricao = categoria.getNome();
    }
}
