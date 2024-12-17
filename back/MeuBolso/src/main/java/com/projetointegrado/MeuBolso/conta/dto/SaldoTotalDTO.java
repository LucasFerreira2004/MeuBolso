package com.projetointegrado.MeuBolso.conta.dto;

import java.math.BigDecimal;

public class SaldoTotalDTO {
    private BigDecimal saldo;

    public SaldoTotalDTO(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
