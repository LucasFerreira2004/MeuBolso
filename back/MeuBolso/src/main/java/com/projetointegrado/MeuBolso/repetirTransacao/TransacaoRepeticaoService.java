package com.projetointegrado.MeuBolso.repetirTransacao;

import com.projetointegrado.MeuBolso.repetirTransacao.GerarTransacoesFixas.GerarTransacoesFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.GerarTransacoesFixas.IGerarTransacoesStrategy;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoRepeticaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    public List<Transacao> gerarTransacoes(LocalDate data, String userId) {
        // Obtém todas as transações normais no período
        System.out.println("TransacaoRecorrenteService -> gerarTransacoes");
        List<Transacao> transacoesNormais = transacaoRepository.findAllBeforeDate(data, userId);

        List<TransacaoRecorrente> transacoesRecorrentes = transacaoRecorrenteRepository.findAllByUsuario(userId);
        if (transacoesNormais.isEmpty() || transacoesRecorrentes.isEmpty()) return null;

        try {
            for (TransacaoRecorrente transacaoRecorrente : transacoesRecorrentes) {
                IGerarTransacoesStrategy gerarTransacoesStrategy;
                if (transacaoRecorrente.getDataFinal() == null)
                    gerarTransacoesStrategy = GerarTransacoesFactory.gerarTransacoesStrategy(TipoRepeticao.FIXO);
                else
                    gerarTransacoesStrategy = GerarTransacoesFactory.gerarTransacoesStrategy(TipoRepeticao.PARCELAMENTO);
                gerarTransacoesStrategy.gerarTransacoes(transacaoRecorrente, data);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return transacoesNormais;
    }
}
