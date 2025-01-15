package com.projetointegrado.MeuBolso.meta.dto;

import com.projetointegrado.MeuBolso.meta.Meta;

import java.math.BigDecimal;

public class MetaCardDTO {
    private Long id;
    private String descricao;
    private String urlImg;
    private BigDecimal valorMeta;
    private BigDecimal valorInvestido;

    public MetaCardDTO() {
    }

    public MetaCardDTO(Meta metaEntity) {
        this.id = metaEntity.getId();
        this.descricao = metaEntity.getDescricao();
        this.urlImg = metaEntity.getUrlImg();
        this.valorMeta = metaEntity.getValorMeta();
        this.valorInvestido = metaEntity.getValorInvestido();
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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
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
}
