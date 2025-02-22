package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import java.time.LocalDate;

public class AvancoDiarioStrategy implements IAvancoDataStrategy {

    @Override
    public LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro, Integer qtdAvancos) {
        dataAtual = dataAtual.plusDays(qtdAvancos);
        return dataAtual;
    }
}
