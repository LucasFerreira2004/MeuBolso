package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import java.time.LocalDate;

public class AvancoSemanal implements IAvancoDataStrategy {
    @Override
    public LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro, Integer qtdAvancos) {
        dataCadastro = dataCadastro.plusWeeks(qtdAvancos);
        return dataCadastro;
    }
}
