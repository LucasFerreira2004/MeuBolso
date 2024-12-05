package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.tipoConta.TipoConta;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal saldo;
    private String nome_banco;

    @ManyToOne
    @JoinColumn(name = "tipo_conta")
    private TipoConta tipo_conta;

    public Conta(Long id, BigDecimal saldo, String nome_banco, TipoConta tipo_conta) {
        this.id = id;
        this.saldo = saldo;
        this.nome_banco = nome_banco;
        this.tipo_conta = tipo_conta;
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

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getNome_banco() {
        return nome_banco;
    }

    public void setNome_banco(String nome_banco) {
        this.nome_banco = nome_banco;
    }

    public TipoConta getTipo_conta() {
        return tipo_conta;
    }

    public void setTipo_conta(TipoConta tipo_conta) {
        this.tipo_conta = tipo_conta;
    }
}
