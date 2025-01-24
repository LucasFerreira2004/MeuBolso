package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoRepeticaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;

    public List<Transacao> gerarTransacoes(LocalDate data, String userId) {
        // Obtém todas as transações normais no período
        System.out.println("TransacaoRepeticaoService -> gerarTransacoes");
        List<Transacao> transacoesNormais = transacaoRepository.findAllBeforeDate(data, userId);

        List<TransacaoFixa> transacoesFixas = transacaoFixaRepository.findAllByUsuario(userId);
        if (transacoesNormais.isEmpty() || transacoesFixas.isEmpty()) return null;

        for (TransacaoFixa transacaoFixa : transacoesFixas) {
            gerarTransacoesFixas(transacaoFixa, data);
        }

        return transacoesNormais;
    }
    private void gerarTransacoesFixas(TransacaoFixa transacaoFixa, LocalDate dataBusca) {
        System.out.println("TransacaoRepeticaoService -> gerarTransacoesFixas");
        LocalDate dataUltimaExecucao = transacaoFixa.getUltimaExecucao() != null
                ? avancarData(transacaoFixa.getUltimaExecucao(), transacaoFixa.getPeriodicidade())
                : transacaoFixa.getDataCadastro();

        while (!dataUltimaExecucao.isAfter(dataBusca)) {
            Transacao novaTransacao = new Transacao(transacaoFixa, dataUltimaExecucao);
            transacaoRepository.save(novaTransacao);
            transacaoFixa.setUltimaExecucao(dataUltimaExecucao);

            dataUltimaExecucao = avancarData(dataUltimaExecucao, transacaoFixa.getPeriodicidade());
        }
        System.out.println("TransacaoRepeticaoService -> gerarTransacoesFixas -> ultimaExecucao = " + transacaoFixa.getUltimaExecucao());
        transacaoFixaRepository.save(transacaoFixa);
    }

    private LocalDate avancarData(LocalDate data, Periodicidade periodicidade) {
        System.out.println("TransacaoRepeticaoService -> avancarData");
        switch (periodicidade) {
            case DIARIO:
                return data.plusDays(1);
            case SEMANAL:
                return data.plusWeeks(1);
            case MENSAL:
                return data.plusMonths(1);
            default:
                throw new IllegalArgumentException("Periodicidade desconhecida");
        }
    }
}
