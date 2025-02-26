package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class TransacaoRecorrente {
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

    @Column(name = "data_final")
    private LocalDate dataFinal;

    @Column(name = "qtd_parcelas")
    private Integer qtdParcelas;

    @Column(nullable = false, name = "tipo_repeticao")
    @Enumerated(EnumType.STRING)
    private TipoRepeticao tipoRepeticao;

    @OneToMany(mappedBy = "transacaoRecorrente", cascade = CascadeType.REMOVE)
    private List<Transacao> transacoes;

    @Column(name = "ativa", nullable = false, columnDefinition = "boolean default true")
    private Boolean ativa;

    //no futuro poderia ter dois construturores, um que recebe um transaçãoFixaSave DTO e outro que recebe um transacaoParceladaSaveDTO
    public TransacaoRecorrente(Long id, BigDecimal valor, TipoTransacao tipo, LocalDate dataCadastro, String descricao, Conta conta, Categoria categoria, Periodicidade periodicidade, Usuario usuario, Integer qtdParcelas, TipoRepeticao tipoRepeticao) {
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
        this.qtdParcelas = qtdParcelas;
        this.tipoRepeticao = tipoRepeticao;
        this.ativa = true;

        if (qtdParcelas == null) return;
        IAvancoDataStrategy avancoStrategy = AvancoDataFactory.getStrategy(this.periodicidade);
        this.dataFinal = avancoStrategy.avancarData(this.dataCadastro, this.dataCadastro, this.qtdParcelas - 1);
    }

    public TransacaoRecorrente() {}

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

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(Integer qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public TipoRepeticao getTipoRepeticao() {
        return tipoRepeticao;
    }

    public void setTipoRepeticao(TipoRepeticao tipoRepeticao) {
        this.tipoRepeticao = tipoRepeticao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
}
