package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "mes", "ano", "categoria_id"})})
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToOne
    @JoinColumn(nullable = false, name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false, name = "mes")
    private Integer mes;

    @Column(nullable = false, name = "ano")
    private Integer ano;

    @Column(nullable = false, name = "valor_estimado")
    private BigDecimal valorEstimado;

    private BigDecimal valorGasto;
    private BigDecimal valorRestante;
    private BigDecimal progresso;

    @ManyToOne
    @Valid
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    public Orcamento() {
    }

    public Orcamento(Categoria categoria, Integer mes, Integer ano, BigDecimal valorEstimado, Usuario usuario) {
        this.categoria = categoria;
        this.descricao = categoria.getNome();
        this.mes = mes;
        this.ano = ano;
        this.valorEstimado = valorEstimado;
        this.usuario = usuario;
        this.valorGasto = BigDecimal.ZERO;
        this.valorRestante = BigDecimal.ZERO;
        this.progresso = BigDecimal.ZERO;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.setDescricao(categoria.getNome());
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void updateValores(BigDecimal gastoTotal) {
        this.valorGasto = gastoTotal;
        this.valorRestante = this.valorEstimado.subtract(gastoTotal);
        this.updateProgresso();
    }

    public BigDecimal getProgresso() {
        return progresso;
    }

    public void updateProgresso() {
        if (this.valorEstimado != null && this.valorEstimado.compareTo(BigDecimal.ZERO) > 0) {
            this.progresso = this.valorGasto
                    .divide(this.valorEstimado, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.progresso = BigDecimal.ZERO;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Orcamento orcamento = (Orcamento) o;
        return Objects.equals(id, orcamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
