package com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes;

import com.projetointegrado.MeuBolso.transacaoRecorrente.TipoRepeticao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerarTransacoesFactory {
    @Autowired
    FixasGerarTransacoesStrategy fixasGerarTransacoesStrategy;

    @Autowired
    ParceladasGerarTransacoesStrategy parceladasGerarTransacoesStrategy;

    public GerarTransacoesFactory() {}

    public IGerarTransacoesStrategy gerarTransacoesStrategy(TipoRepeticao tipoRepeticao) {
        switch (tipoRepeticao) {
            case FIXO:
                return fixasGerarTransacoesStrategy;
            case PARCELAMENTO:
                return parceladasGerarTransacoesStrategy;
            default:
                throw new IllegalArgumentException("TipoRepeticao desconhecida");
        }
    }
}
