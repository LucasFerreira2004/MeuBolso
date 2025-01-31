package com.projetointegrado.MeuBolso.repetirTransacao.GerarTransacoesFixas;

import com.projetointegrado.MeuBolso.transacaoRecorrente.TipoRepeticao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerarTransacoesFactory {
    @Autowired
    FixasGerarTransacoes fixasGerarTransacoes;

    @Autowired
    ParceladasGerarTransacoes parceladasGerarTransacoes;

    public GerarTransacoesFactory() {}

    public IGerarTransacoesStrategy gerarTransacoesStrategy(TipoRepeticao tipoRepeticao) {

        switch (tipoRepeticao) {
            case FIXO:
                return fixasGerarTransacoes;
            case PARCELAMENTO:
                return parceladasGerarTransacoes;
            default:
                throw new IllegalArgumentException("Periodicidade desconhecida");
        }
    }
}
