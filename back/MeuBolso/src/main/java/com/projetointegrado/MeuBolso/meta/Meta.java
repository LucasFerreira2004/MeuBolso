package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.TransacaoMeta;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
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

    private String comentario;
    private BigDecimal progresso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "meta")
    private List<TransacaoMeta> transacoes = new ArrayList<>();

    public Meta(){
    }

    public Meta(Long id, BigDecimal valorMeta, String descricao, String urlImg, Usuario usuario) {
        this.valorMeta = valorMeta;
        this.valorInvestido = BigDecimal.ZERO;
        this.urlImg = urlImg;
        this.descricao = descricao;
        this.usuario = usuario;
        this.comentario = null;
        this.progresso = BigDecimal.ZERO;
    }

    public Meta(Long id, BigDecimal valorMeta, BigDecimal valorInvestido, String descricao, String urlImg, Usuario usuario, String comentario) {
        this.id = id;
        this.valorMeta = valorMeta;
        this.valorInvestido = valorInvestido;
        this.urlImg = urlImg;
        this.descricao = descricao;
        this.usuario = usuario;
        this.comentario = comentario;
        this.progresso = BigDecimal.ZERO;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getProgresso() {
        return progresso;
    }

    public void setProgresso() {
        if (this.valorMeta.compareTo(BigDecimal.ZERO) > 0) { // Evita divisão por zero
            this.progresso = this.valorInvestido
                    .divide(this.valorMeta, 4, RoundingMode.HALF_UP)                     .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.progresso = BigDecimal.ZERO; // Se a meta for 0, progresso é 0%
        }
    }

    public List<TransacaoMeta> getTransacoes() {
        return transacoes;
    }

    public void addTransacoes(TransacaoMeta transacao) {
        this.transacoes.add(transacao);
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
