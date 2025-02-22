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
    public void gerarTransacoes(LocalDate data, String userId) {
        ReentrantLock lock = locks.computeIfAbsent(userId, k -> new ReentrantLock());

        lock.lock();
        try {
            List<TransacaoRecorrente> transacoesRecorrentes = transacaoRecorrenteRepository.findAllByUsuario(userId);
            if (transacoesRecorrentes.isEmpty()) return;

            for (TransacaoRecorrente transacaoRecorrente : transacoesRecorrentes) {
                transacaoRecorrente = transacaoRecorrenteRepository.findById(transacaoRecorrente.getId()).orElse(null);
                if (transacaoRecorrente == null) continue;

                List<Transacao> transacoes = transacaoRepository.findAllInRange(
                        data.with(TemporalAdjusters.firstDayOfMonth()),
                        data.with(TemporalAdjusters.lastDayOfMonth()),
                        userId
                );

                TransacaoRecorrente finalTransacaoRecorrente = transacaoRecorrente;
                boolean transacaoExistente = transacoes.stream()
                        .anyMatch(t -> t.getTransacaoRecorrente() != null &&
                                t.getTransacaoRecorrente().getId().equals(finalTransacaoRecorrente.getId()));

                if (transacaoExistente || !transacaoRecorrente.getAtiva()) continue;

                //chama a factory de strategies de gerarTransacoes com base no tipo de repetição (FIXA, PARCELADA)
                IGerarTransacoesStrategy gerarTransacoesStrategy = gerarTransacoesFactory.gerarTransacoesStrategy(transacaoRecorrente.getTipoRepeticao());
                //executa a strategy
                gerarTransacoesStrategy.gerarTransacoes(transacaoRecorrente, data); //executa o método da strategy
            }
        } finally {
            lock.unlock();
        }
    }
}
