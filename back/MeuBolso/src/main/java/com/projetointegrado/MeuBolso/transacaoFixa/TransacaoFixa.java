package com.projetointegrado.MeuBolso.transacaoFixa;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class TransacaoFixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate dataCadastro;

    @Column(nullable = false) //especificarTamanho da descrição
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Periodicidade periodicidade;

    @Column(nullable = true, name = "ultima_execucao")
    private LocalDate ultimaExecucao; //representa a data que foi chamada pela última vez para realizar a cricação de transacoes

    @OneToMany(mappedBy = "transacaoFixa", cascade = CascadeType.REMOVE)
    private List<Transacao> transacoes;

    public TransacaoFixa(Long id, BigDecimal valor, TipoTransacao tipo, LocalDate dataCadastro, String descricao, Conta conta, Categoria categoria, Periodicidade periodicidade, Usuario usuario) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
        this.descricao = descricao;
        this.conta = conta;
        this.categoria = categoria;
        this.periodicidade = periodicidade;
        this.usuario = usuario;
        this.ultimaExecucao = null;
    }

    public TransacaoFixa() {}

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public LocalDate getUltimaExecucao() {
        return ultimaExecucao;
    }

    public void setUltimaExecucao(LocalDate ultimaExecucao) {
        this.ultimaExecucao = ultimaExecucao;
    }
}
