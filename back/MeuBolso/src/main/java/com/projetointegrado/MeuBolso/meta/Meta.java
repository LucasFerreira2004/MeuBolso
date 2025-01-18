package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valorMeta;

    @Column(nullable = false)
    private BigDecimal valoInvestido;
    private String urlImg;
    private String descricao;

    @ManyToOne
    @Valid
    @JoinColumn(nullable = false)
    private Usuario usuario;

    public Meta(){
    }

    public Meta(BigDecimal valorMeta, BigDecimal valoInvestido, String urlImg, String descricao) {
        this.valorMeta = valorMeta;
        this.valoInvestido = valoInvestido;
        this.urlImg = urlImg;
        this.descricao = descricao;
    }

    public Meta(BigDecimal valorMeta, BigDecimal valoInvestido, String urlImg) {
        this.valorMeta = valorMeta;
        this.valoInvestido = valoInvestido;
        this.urlImg = urlImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
    }

    public BigDecimal getValorInvestido() {
        return valoInvestido;
    }

    public void setValorInvestido(BigDecimal valoInvestido) {
        this.valoInvestido = valoInvestido;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Meta meta = (Meta) o;
        return Objects.equals(id, meta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
