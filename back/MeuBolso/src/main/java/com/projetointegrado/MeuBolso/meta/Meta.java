package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"usuario_id", "descricao"}) })
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.01", message = "O valor de uma meta deve ser maior que 0.")
    @Column(nullable = false)
    private BigDecimal valorMeta;

    @DecimalMin(value = "0.00", message = "O valor mínimo de uma meta é 0.")
    @Column(nullable = false)
    private BigDecimal valorInvestido;
    private String urlImg;

    @NotBlank(message = "Uma meta deve possuir uma descricao.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Meta(){
    }

    public Meta(Long id, BigDecimal valorMeta, String descricao, String urlImg, Usuario usuario) {
        this.valorMeta = valorMeta;
        this.valorInvestido = BigDecimal.ZERO;
        this.urlImg = urlImg;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public Meta(MetaDTO metaDTO) {
        BeanUtils.copyProperties(metaDTO, this);
    }

    public Meta(MetaPostDTO meta) {
        this.valorMeta = meta.getValorMeta();
        this.valorInvestido = BigDecimal.ZERO;
        this.descricao = meta.getDescricao();
        this.urlImg = meta.getUrlImg();
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
        return valorInvestido;
    }

    public void setValorInvestido(BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
