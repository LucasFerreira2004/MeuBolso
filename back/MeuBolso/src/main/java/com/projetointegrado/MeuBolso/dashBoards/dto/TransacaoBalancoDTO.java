package com.projetointegrado.MeuBolso.dashBoards.dto;

import java.math.BigDecimal;

public record TransacaoBalancoDTO(
        int ano,
        int mes,
        BigDecimal despesas,
        BigDecimal receitas
) {
}
