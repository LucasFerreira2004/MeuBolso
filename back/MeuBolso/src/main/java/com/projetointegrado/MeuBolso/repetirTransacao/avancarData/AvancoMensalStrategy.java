package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class AvancoMensalStrategy implements IAvancoDataStrategy {
    @Override
    public LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro, Integer qtdAvancos) {
        LocalDate novaData = dataAtual.plusMonths(qtdAvancos);
        int ultimoDiaDoMesAtual = novaData.lengthOfMonth();
        if (dataCadastro.getDayOfMonth() > ultimoDiaDoMesAtual) {
            novaData = novaData.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            novaData = novaData.withDayOfMonth(dataCadastro.getDayOfMonth());
        }
        return novaData;
    }
}
