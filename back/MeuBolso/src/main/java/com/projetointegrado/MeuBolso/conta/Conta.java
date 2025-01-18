package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;
import org.springframework.beans.BeanUtils;
import com.projetointegrado.MeuBolso.transacao.Transacao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Formula("(SELECT SUM(t.valor) FROM transacao t WHERE t.conta_origem = id) and data <= dataAtual")
    private BigDecimal saldo;

    @ManyToOne
    private TipoConta tipo_conta;

    @ManyToOne
    private Banco banco;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE)
    private List<Transacao> transacoes;

    @Transient //indica que o valor não será persistido no banco de dados.
    private Date dataAtual;

    public Conta(Long id, BigDecimal saldo, TipoConta tipo_conta, Banco banco, Usuario usuario) {
        this.id = id;
        this.saldo = saldo;
        this.tipo_conta = tipo_conta;
        this.banco = banco;
        this.usuario = usuario;
    }
    public Conta(ContaDTO contaDTO) {
        BeanUtils.copyProperties(contaDTO, this);
    }
    public Conta() {}

    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
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

    public Date getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(Date dataAtual) {
        this.dataAtual = dataAtual;
    }
}
