package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"usuario_id", "nome"}) })
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoCategoria tipoCategoria;

    @Column(nullable = false)
    private String cor;

    @Column(name = "ativa", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativa;

    @Column(name = "interna_sistema", nullable = false, columnDefinition = "boolean default false")
    private Boolean internaSistema;

    @ManyToOne
    @Valid
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    public Categoria() {
    }

    public Categoria(Long id, String nome, TipoCategoria tipoCategoria, String cor, Boolean ativa, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.tipoCategoria = tipoCategoria;
        this.cor = cor;
        this.ativa = ativa;
        this.usuario = usuario;
    }

    public Categoria(Long id, String nome, TipoCategoria tipoCategoria, String cor, Boolean ativa, Usuario usuario, Boolean internaSistema) {
        this.id = id;
        this.nome = nome;
        this.tipoCategoria = tipoCategoria;
        this.cor = cor;
        this.ativa = ativa;
        this.usuario = usuario;
        this.internaSistema = internaSistema;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getInternaSistema() {
        return internaSistema;
    }

    public void setInternaSistema(Boolean internaSistema) {
        this.internaSistema = internaSistema;
    }
}
