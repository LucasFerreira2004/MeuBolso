package com.projetointegrado.MeuBolso.repetirTransacao;

import com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes.GerarTransacoesFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes.IGerarTransacoesStrategy;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransacaoRepeticaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    @Autowired
    private GerarTransacoesFactory gerarTransacoesFactory;

    @Transactional()
    public void gerarTransacoes(LocalDate data, String userId) {
        // Obtém todas as transações normais no período
        System.out.println("TransacaoRecorrenteService -> gerarTransacoes");

        List<TransacaoRecorrente> transacoesRecorrentes = transacaoRecorrenteRepository.findAllByUsuario(userId);
        if (transacoesRecorrentes.isEmpty()) return;
        try {
            for (TransacaoRecorrente transacaoRecorrente : transacoesRecorrentes) {
                if (!transacaoRecorrente.getAtiva()) continue;
                IGerarTransacoesStrategy gerarTransacoesStrategy = gerarTransacoesFactory.gerarTransacoesStrategy(transacaoRecorrente.getTipoRepeticao());
                gerarTransacoesStrategy.gerarTransacoes(transacaoRecorrente, data);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}