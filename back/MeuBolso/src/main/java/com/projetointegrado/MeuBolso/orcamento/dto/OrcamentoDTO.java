package com.projetointegrado.MeuBolso.orcamento.dto;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.orcamento.Orcamento;

import java.math.BigDecimal;

public class OrcamentoDTO {
    private Long id;
    private String descricao;
    private BigDecimal valorEstimado;
    private BigDecimal valorGasto;
    private BigDecimal valorRestante;
    private String mesAno;
    private CategoriaDTO categoriaDTO;

    public OrcamentoDTO() {
    }

    public OrcamentoDTO(Long id, String descricao, BigDecimal valorEstimado, BigDecimal valorGasto, BigDecimal valorRestante, String mesAno, CategoriaDTO categoriaDTO) {
        this.id = id;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.valorGasto = valorGasto;
        this.valorRestante = valorRestante;
        this.mesAno = mesAno;
        this.categoriaDTO = categoriaDTO;
    }

    public OrcamentoDTO(Orcamento orcamento) {
        this.id = orcamento.getId();
        this.descricao = orcamento.getDescricao();
        this.valorEstimado = orcamento.getValorEstimado();
        this.valorGasto = orcamento.getValorGasto();
        this.valorRestante = orcamento.getValorRestante();
        this.categoriaDTO = new CategoriaDTO(orcamento.getCategoria());
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao() {
        this.descricao = categoriaDTO.getNome();
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

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }

    public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
        this.setDescricao();
    }
}
