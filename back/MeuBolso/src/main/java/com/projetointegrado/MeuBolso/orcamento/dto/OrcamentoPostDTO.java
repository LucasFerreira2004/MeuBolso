package com.projetointegrado.MeuBolso.orcamento.dto;

import com.projetointegrado.MeuBolso.categoria.Categoria;

import java.math.BigDecimal;

public class OrcamentoPostDTO {
    private String descricao;
    private BigDecimal valorEstimado;
    private String mesAno;
    private Categoria categoria;

    public OrcamentoPostDTO() {
    }

    public OrcamentoPostDTO(BigDecimal valorEstimado, String mesAno, Categoria categoria) {
        this.valorEstimado = valorEstimado;
        this.mesAno = mesAno;
        this.categoria = categoria;
        this.descricao = categoria.getNome();
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

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
