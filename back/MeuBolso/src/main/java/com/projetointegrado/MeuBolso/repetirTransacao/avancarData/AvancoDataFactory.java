package com.projetointegrado.MeuBolso.repetirTransacao.avancarData;

import com.projetointegrado.MeuBolso.transacaoRecorrente.Periodicidade;

public class AvancoDataFactory {
    public static IAvancoDataStrategy getStrategy(Periodicidade periodicidade) {
        switch (periodicidade) {
            case DIARIO:
                return new AvancoDiarioStrategy();
            case SEMANAL:
                return new AvancoSemanalStrategy();
            case MENSAL:
                return new AvancoMensalStrategy();
            case ULTIMO_DIA_MES:
                return new AvancoUltimoIDiaMesStrategy();
            default:
                throw new IllegalArgumentException("Periodicidade desconhecida");
        }
    }
}
