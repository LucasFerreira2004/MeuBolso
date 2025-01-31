package com.projetointegrado.MeuBolso.dashBoards.dto;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;

import java.math.BigDecimal;
import java.util.List;

public record CategoriaExpandedDTO(
        Long id,
        String cor,
        String nome,
        BigDecimal valorTotal,
        BigDecimal percentual,
        List<TransacaoDTO> transacaoes
) {
}
