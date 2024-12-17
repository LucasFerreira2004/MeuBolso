package com.projetointegrado.MeuBolso.conta.dto;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;

import java.math.BigDecimal;

public class ContaMinDTO {
    String iconeUrl;
    String nomeBanco;
    BigDecimal saldo;

    public ContaMinDTO(Conta conta) {
        TipoConta tipo = conta.getTipo_conta();
        Banco banco = conta.getBanco();
        this.saldo = conta.getSaldo();
        this.nomeBanco = banco.getNome();
        this.iconeUrl = banco.getIconeUrl();
    }
    public String getIconeUrl() {
        return iconeUrl;
    }

    public void setIconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
