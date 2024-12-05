package com.projetointegrado.MeuBolso.conta.dto;

import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.tipoConta.TipoConta;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class ContaDTO {
    private Long id;
    private BigDecimal saldo;
    private String nome_banco;
    private TipoConta tipo_conta;

    public ContaDTO(Conta conta) {
        this.id = conta.getId();
        this.saldo = conta.getSaldo();
        this.nome_banco = conta.getNome_banco();
        this.tipo_conta = conta.getTipo_conta();
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
