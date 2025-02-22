package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class AvancoUltimoIDiaMesStrategy implements IAvancoDataStrategy{
    @Override
    public LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro, Integer qtdAvancos) {
        LocalDate proximaData = dataAtual.plusMonths(qtdAvancos);
        return proximaData.with(TemporalAdjusters.lastDayOfMonth());
    }
}
