package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class AvancoMensal implements IAvancoDataStrategy {
    @Override
    public LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro, Integer qtdAvancos) {
        try{
            LocalDate novaData = dataAtual.plusMonths(qtdAvancos); // Avança um mês
            int ultimoDiaDoMesAtual = novaData.lengthOfMonth();
            System.out.println(ultimoDiaDoMesAtual);
            if (dataCadastro.getDayOfMonth() > ultimoDiaDoMesAtual) {
                novaData = novaData.with(TemporalAdjusters.lastDayOfMonth());
            } else {
                novaData = novaData.withDayOfMonth(dataCadastro.getDayOfMonth());
            }
            return novaData;
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
