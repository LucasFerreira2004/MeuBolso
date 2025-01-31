package com.projetointegrado.MeuBolso.dashBoards.dto;

import java.math.BigDecimal;

public record CategoriaMinDTO(
        Long id,
        String cor,
        String nome,
        BigDecimal valorTotal,
        BigDecimal percentual
) {
}
