package com.projetointegrado.MeuBolso.transacaoFixa;

import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
                ? avancarData(transacaoFixa.getUltimaExecucao(), transacaoFixa.getDataCadastro(), transacaoFixa.getPeriodicidade())
                : transacaoFixa.getDataCadastro();

        while (!dataUltimaExecucao.isAfter(dataBusca)) {
            Transacao novaTransacao = new Transacao(transacaoFixa, dataUltimaExecucao);
            transacaoRepository.save(novaTransacao);
            transacaoFixa.setUltimaExecucao(dataUltimaExecucao);

            dataUltimaExecucao = avancarData(dataUltimaExecucao, transacaoFixa.getDataCadastro(),transacaoFixa.getPeriodicidade());
        }
        System.out.println("TransacaoRepeticaoService -> gerarTransacoesFixas -> ultimaExecucao = " + transacaoFixa.getUltimaExecucao());
        transacaoFixaRepository.save(transacaoFixa);
    }

    private LocalDate avancarData(LocalDate dataAtual, LocalDate dataCadastro,Periodicidade periodicidade) {
        System.out.println("TransacaoRepeticaoService -> avancarData");
        switch (periodicidade) {
            case DIARIO:
                return dataAtual.plusDays(1);
            case SEMANAL:
                return dataAtual.plusWeeks(1);
            case MENSAL:
                try {
                    LocalDate novaData = dataAtual.plusMonths(1); // Avança um mês
                    int ultimoDiaDoMesAtual = novaData.lengthOfMonth();
                    System.out.println(ultimoDiaDoMesAtual);

                    if (dataCadastro.getDayOfMonth() > ultimoDiaDoMesAtual) {
                        novaData = novaData.with(TemporalAdjusters.lastDayOfMonth());
                    } else {
                        novaData = novaData.withDayOfMonth(dataCadastro.getDayOfMonth());
                    }
                    return novaData;
                } catch(Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            default:
                throw new IllegalArgumentException("Periodicidade desconhecida");
        }
    }
}
