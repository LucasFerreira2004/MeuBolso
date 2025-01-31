package com.projetointegrado.MeuBolso.dashboard.dto;

import java.math.BigDecimal;

public record TransacaoBalancoDTO(
        int ano,
        int mes,
        BigDecimal despesas,
        BigDecimal receitas
) {
}
