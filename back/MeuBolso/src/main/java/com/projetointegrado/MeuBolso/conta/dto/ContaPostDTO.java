package com.projetointegrado.MeuBolso.conta.dto;

import java.math.BigDecimal;

public class ContaPostDTO {
    private BigDecimal saldo;
    private Long id_banco;
    private Long id_tipo_conta;
    private Long id_usuario;
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

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
}
