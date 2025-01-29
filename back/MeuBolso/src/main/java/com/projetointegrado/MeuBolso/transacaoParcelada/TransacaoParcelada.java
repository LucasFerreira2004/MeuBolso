package com.projetointegrado.MeuBolso.transacaoParcelada;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacaoFixa.Periodicidade;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class TransacaoParcelada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate dataInicial;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate dataFinal;

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

    public TransacaoParcelada(Long id, BigDecimal valor, TipoTransacao tipo, LocalDate dataInicial, String descricao, Conta conta, Categoria categoria, Usuario usuario, Periodicidade periodicidade) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.dataInicial = dataInicial;
        this.descricao = descricao;
        this.conta = conta;
        this.categoria = categoria;
        this.usuario = usuario;
        this.periodicidade = periodicidade;
    }

    public TransacaoParcelada() {}
}
