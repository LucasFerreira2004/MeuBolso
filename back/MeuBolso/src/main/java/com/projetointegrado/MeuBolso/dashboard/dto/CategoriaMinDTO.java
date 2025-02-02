package com.projetointegrado.MeuBolso.dashboard.dto;

import java.math.BigDecimal;

public record CategoriaMinDTO(
        Long id,
        String cor,
        String nome,
        BigDecimal valorTotal,
        BigDecimal percentual
) {
}
