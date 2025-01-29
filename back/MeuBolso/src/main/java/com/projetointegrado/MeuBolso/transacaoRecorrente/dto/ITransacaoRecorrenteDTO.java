package com.projetointegrado.MeuBolso.transacaoRecorrente.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ITransacaoRecorrenteDTO {
        BigDecimal valor();
        String tipoTransacao();
        LocalDate data();
        Long contaId();
        Long categoriaId();
        String descricao();
        String periodicidade();
        Integer qtdParcelas();
}
