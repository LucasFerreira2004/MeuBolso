package com.projetointegrado.MeuBolso.meta.dto;

import com.projetointegrado.MeuBolso.meta.Meta;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class MetaPostDTO {
    @NotNull(message = "O valor não pode ser nulo, deve ser do tipo BigDecimal. ex: 9.99.")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser maior que 0.")
    private BigDecimal valorMeta;

    @NotBlank(message = "A decricao de uma meta não pode ser vazia.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    private String urlImg;

    public MetaPostDTO() {
    }

    public MetaPostDTO(Meta meta) {
        BeanUtils.copyProperties(meta, this);
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
