package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.transacao.Transacao;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"nome", "tipo_categoria"}) })
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoCategoria tipoCategoria;
    @Column(nullable = false)
    private String cor;
    @Column(name = "ativa", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativa;

    public Categoria() {
    }

    public Categoria(Long id, String nome, TipoCategoria tipoCategoria, String cor, Boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.tipoCategoria = tipoCategoria;
        this.cor = cor;
        this.ativa = ativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        return tipoCategoria;
    }

    public void setTipo(TipoCategoria tipo) {
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public TipoCategoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
}
