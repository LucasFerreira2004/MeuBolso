package com.projetointegrado.MeuBolso.categoria;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
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

    public Categoria() {
    }

    public Categoria(Long id, String nome, TipoCategoria tipoCategoria, String cor) {
        this.id = id;
        this.nome = nome;
        this.tipoCategoria = tipoCategoria;
        this.cor = cor;
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
}
