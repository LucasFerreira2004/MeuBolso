package com.projetointegrado.MeuBolso.orcamento.dto;

import java.math.BigDecimal;

public class OrcamentoPostDTO {
    private BigDecimal valorEstimado;
    private String mesAno;
    private Long idCategoria;

    public OrcamentoPostDTO() {
    }

    public OrcamentoPostDTO(BigDecimal valorEstimado, String mesAno, Long idCategoria) {
        this.valorEstimado = valorEstimado;
        this.mesAno = mesAno;
        this.idCategoria = idCategoria;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
}
