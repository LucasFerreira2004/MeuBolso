package com.projetointegrado.MeuBolso.repetirTransacao.GerarTransacoesFixas;

import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;

import java.time.LocalDate;

public interface IGerarTransacoesStrategy {
    void gerarTransacoes (TransacaoRecorrente transacaoRecorrente, LocalDate dataBusca);
}
