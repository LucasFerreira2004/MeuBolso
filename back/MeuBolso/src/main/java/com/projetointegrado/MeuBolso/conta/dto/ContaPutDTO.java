package com.projetointegrado.MeuBolso.conta.dto;

import java.math.BigDecimal;

public class ContaPutDTO {
    private BigDecimal saldo;
    private Long id_banco;
    private Long id_tipo_conta;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getId_banco() {
        return id_banco;
    }

    public void setId_banco(Long id_banco) {
        this.id_banco = id_banco;
    }

    public Long getId_tipo_conta() {
        return id_tipo_conta;
    }

    public void setId_tipo_conta(Long id_tipo_conta) {
        this.id_tipo_conta = id_tipo_conta;
    }
}
