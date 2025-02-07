package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import com.projetointegrado.MeuBolso.transacao.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"usuario_id", "descricao"}) })
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "descricao")
    private String descricao;

    @Transient
    private BigDecimal saldo;

    @ManyToOne
    private TipoConta tipo_conta;

    @ManyToOne
    private Banco banco;

    @ManyToOne
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Transacao> transacoes;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<TransacaoRecorrente> transacoesRecorrentes;

   // @Transient //indica que o valor não será persistido no banco de dados.
    //private Date dataAtual;

    //mapear as transacoes_recorrentes aqui.

    public Conta(Long id, TipoConta tipo_conta, Banco banco, String descricao, Usuario usuario) {
        this.id = id;
        this.tipo_conta = tipo_conta;
        this.banco = banco;
        this.descricao = descricao;
        this.usuario = usuario;
        this.transacoes = new ArrayList<>();
    }
    public Conta(ContaDTO contaDTO) {
        BeanUtils.copyProperties(contaDTO, this);
    }
    public Conta() {
        this.transacoes = new ArrayList<>();
    }

    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo(LocalDate data) {
        BigDecimal saldo = BigDecimal.ZERO;
        if (transacoes == null || transacoes.isEmpty()) return saldo;
        for (Transacao transacao : transacoes) {
            if (!transacao.getData().isAfter(data)) { //retorna true para todas as datas antes de data
                if (transacao.getTipo() == TipoTransacao.RECEITA)
                    saldo = saldo.add(transacao.getValor());
                else if (transacao.getTipo() == TipoTransacao.DESPESA)
                    saldo = saldo.subtract(transacao.getValor());
            }
        }
        return saldo;
    }
//    public void setSaldo(BigDecimal saldo) {
//        this.saldo = saldo; //tirar essa lógica depois.
//    }

    public TipoConta getTipo_conta() {
        return tipo_conta;
    }

    public void setTipo_conta(TipoConta tipo_conta) {
        this.tipo_conta = tipo_conta;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

//    public Date getDataAtual() {
//        return dataAtual;
//    }
//
//    public void setDataAtual(Date dataAtual) {
//        this.dataAtual = dataAtual;
//    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
