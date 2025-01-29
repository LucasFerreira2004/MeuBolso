package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import com.projetointegrado.MeuBolso.transacaoRecorrente.Periodicidade;

public class AvancoDataFactory {
    public static IAvancoDataStrategy getStrategy(Periodicidade periodicidade) {
        switch (periodicidade) {
            case DIARIO:
                return new AvancoDiario();
            case SEMANAL:
                return new AvancoSemanal();
            case MENSAL:
                return new AvancoMensal();
            default:
                throw new IllegalArgumentException("Periodicidade desconhecida");
        }
    }
}
