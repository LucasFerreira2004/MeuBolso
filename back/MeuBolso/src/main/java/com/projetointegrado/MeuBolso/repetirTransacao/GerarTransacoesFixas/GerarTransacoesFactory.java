package com.projetointegrado.MeuBolso.repetirTransacao.GerarTransacoesFixas;

import com.projetointegrado.MeuBolso.repetirTransacao.TipoRepeticao;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;

public class GerarTransacoesFactory {
    public static IGerarTransacoesStrategy gerarTransacoesStrategy(TipoRepeticao tipoRepeticao) {
        switch (tipoRepeticao) {
            case FIXO:
                return new FixasGerarTransacoes();
            case PARCELAMENTO:
                return new ParceladasGerarTransacoes();
            default:
                throw new IllegalArgumentException("Periodicidade desconhecida");
        }
    }
}
