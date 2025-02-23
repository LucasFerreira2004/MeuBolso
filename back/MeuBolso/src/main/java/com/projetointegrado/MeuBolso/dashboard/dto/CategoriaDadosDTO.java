package com.projetointegrado.MeuBolso.dashboard.dto;

import com.projetointegrado.MeuBolso.categoria.Categoria;

import java.math.BigDecimal;

public record CategoriaDadosDTO(
        Categoria categoria,
        BigDecimal valor,
        BigDecimal percentual
) {
}
