package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import java.time.LocalDate;

public interface IAvancoDataStrategy {
    LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro, Integer qtdAvancos);
}
