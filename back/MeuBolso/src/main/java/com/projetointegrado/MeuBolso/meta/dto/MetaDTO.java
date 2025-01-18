package com.projetointegrado.MeuBolso.meta.dto;

import com.projetointegrado.MeuBolso.meta.Meta;

import java.math.BigDecimal;

public class MetaDTO {
    private Long id;
    private BigDecimal valorMeta;
    private BigDecimal valoInvestido;
    private String descricao;

    public MetaDTO() {
    }

    public MetaDTO(Meta metaEntity) {
        this.id = metaEntity.getId();
        this.valorMeta = metaEntity.getValorMeta();
        this.valoInvestido = metaEntity.getValorInvestido();
        this.descricao = metaEntity.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
    }

    public BigDecimal getValoInvestido() {
        return valoInvestido;
    }

    public void setValoInvestido(BigDecimal valoInvestido) {
        this.valoInvestido = valoInvestido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
