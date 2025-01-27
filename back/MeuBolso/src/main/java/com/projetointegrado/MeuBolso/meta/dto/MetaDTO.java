package com.projetointegrado.MeuBolso.meta.dto;

import com.projetointegrado.MeuBolso.meta.Meta;

import java.math.BigDecimal;

public class MetaDTO {
    private Long id;
    private BigDecimal valorMeta;
    private BigDecimal valorInvestido;
    private String descricao;
    private String imgUrl;

    public MetaDTO() {
    }

    public MetaDTO(Meta metaEntity) {
        this.id = metaEntity.getId();
        this.valorMeta = metaEntity.getValorMeta();
        this.valorInvestido = metaEntity.getValorInvestido();
        this.descricao = metaEntity.getDescricao();
        this.imgUrl = metaEntity.getUrlImg();
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

    public BigDecimal getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
