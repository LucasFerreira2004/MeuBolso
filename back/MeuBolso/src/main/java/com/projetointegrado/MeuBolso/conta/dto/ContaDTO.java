package com.projetointegrado.MeuBolso.conta.dto;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.usuario.Usuario;

import java.math.BigDecimal;

public class ContaDTO {
    private Long id;
    private BigDecimal saldo;

    private TipoConta tipo_conta;
    private Banco banco;
    private String descricao;

    public ContaDTO(Conta conta) {
        this.id = conta.getId();
        this.saldo = null;
        this.tipo_conta = conta.getTipo_conta();
        this.banco = conta.getBanco();
        this.descricao = conta.getDescricao();
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
