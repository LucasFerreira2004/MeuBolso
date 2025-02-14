package com.projetointegrado.MeuBolso.conta.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public abstract class ContaSaveDTO {

    @NotNull(message = "Saldo não pode ser vazio")
    @DecimalMin(value = "0.01", message = "O valor do saldo deve ser no mínimo 0.01")
    private BigDecimal saldo;

    @NotNull(message = "Banco não pode ser vazio")
    private Long id_banco;

    @NotNull(message = "Tipo da conta não pode ser vazio")
    private Long id_tipo_conta;

    @NotBlank(message = "Descrição não pode ser vazia")
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
