package com.projetointegrado.MeuBolso.meta.dto;

import java.math.BigDecimal;

public class MetaPostDTO {
    private BigDecimal valorMeta;
    private String descricao;
    private String urlImg;

    public MetaPostDTO() {
    }

    public MetaPostDTO(BigDecimal valorMeta, String descricao, String urlImg) {
        this.valorMeta = valorMeta;
        this.descricao = descricao;
        this.urlImg = urlImg;
    }

    public BigDecimal getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
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
}
