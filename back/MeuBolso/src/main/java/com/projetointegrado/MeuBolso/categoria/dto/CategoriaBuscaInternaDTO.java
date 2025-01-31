package com.projetointegrado.MeuBolso.categoria.dto;

import com.projetointegrado.MeuBolso.categoria.Categoria;

import java.math.BigDecimal;

public record CategoriaBuscaInternaDTO(
        Categoria categoria,
        BigDecimal valor,
        BigDecimal percentual
) {
}
