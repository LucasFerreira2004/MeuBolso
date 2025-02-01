package com.projetointegrado.MeuBolso.dashboard.dto;

import java.math.BigDecimal;

public record SaldoBalancoDTO(
        int ano,
        int mes,
        BigDecimal saldo
) {
}
