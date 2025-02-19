package com.projetointegrado.MeuBolso.repetirTransacao;

import com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes.GerarTransacoesFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes.IGerarTransacoesStrategy;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.TransacaoService;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TransacaoRepeticaoService {
    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private GerarTransacoesFactory gerarTransacoesFactory;

    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Transactional
    public  void gerarTransacoes(LocalDate data, String userId) {
        System.out.println("TransacaoRecorrenteService -> gerarTransacoes");
        List<TransacaoRecorrente> transacoesRecorrentes = transacaoRecorrenteRepository.findAllByUsuario(userId);
        if (transacoesRecorrentes.isEmpty()) return;

        for (TransacaoRecorrente transacaoRecorrente : transacoesRecorrentes) {
            System.out.println("NO LOOP");
            transacaoRecorrente = transacaoRecorrenteRepository.findById(transacaoRecorrente.getId()).orElse(null);
            List<Transacao> transacoes = transacaoRepository.findAllInRange(data.with(TemporalAdjusters.firstDayOfMonth()), data.with(TemporalAdjusters.lastDayOfMonth()), userId);
            boolean transacaoExistente = false;
            if(transacaoRecorrente.getId() != null)
                for (Transacao t : transacoes){
                    if (t.getId().equals(transacaoRecorrente.getId())) {
                        transacaoExistente = true;
                        break;
                    }
                }
            if(transacaoExistente) continue;
            if (!transacaoRecorrente.getAtiva()) continue;
            IGerarTransacoesStrategy gerarTransacoesStrategy = gerarTransacoesFactory.gerarTransacoesStrategy(transacaoRecorrente.getTipoRepeticao());
            gerarTransacoesStrategy.gerarTransacoes(transacaoRecorrente, data);
        }
    }
}