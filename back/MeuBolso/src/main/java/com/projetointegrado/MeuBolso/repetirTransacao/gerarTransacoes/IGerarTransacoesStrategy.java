package com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes;

import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;

import java.time.LocalDate;

public interface IGerarTransacoesStrategy {
    void gerarTransacoes (TransacaoRecorrente transacaoRecorrente, LocalDate dataBusca);
}
