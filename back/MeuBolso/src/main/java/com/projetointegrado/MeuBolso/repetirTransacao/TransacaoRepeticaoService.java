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
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TransacaoRepeticaoService {
    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    @Autowired
    private GerarTransacoesFactory gerarTransacoesFactory;

    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Transactional
    public void gerarTransacoes(LocalDate data, String userId) {
        System.out.println("TransacaoRecorrenteService -> gerarTransacoes");
        ReentrantLock lock = locks.computeIfAbsent(userId, k -> new ReentrantLock());

        lock.lock();
        try {
            System.out.println("bloqueado para userId: " + userId);
            List<TransacaoRecorrente> transacoesRecorrentes = transacaoRecorrenteRepository.findAllByUsuario(userId);
            if (transacoesRecorrentes.isEmpty()) return;
            for (TransacaoRecorrente transacaoRecorrente : transacoesRecorrentes) {
               // transacaoRecorrente.setUltimaExecucao(LocalDate.parse("2025-07-18"));
                if (!transacaoRecorrente.getAtiva()) continue;
                IGerarTransacoesStrategy gerarTransacoesStrategy = gerarTransacoesFactory.gerarTransacoesStrategy(transacaoRecorrente.getTipoRepeticao());
                gerarTransacoesStrategy.gerarTransacoes(transacaoRecorrente, data);
            }
        } finally {
            lock.unlock();
            System.out.println("liberado para userId: " + userId);
        }
    }
}