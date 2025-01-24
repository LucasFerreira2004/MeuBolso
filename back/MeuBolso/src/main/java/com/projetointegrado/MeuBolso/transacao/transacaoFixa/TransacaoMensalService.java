package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TransacaoMensalService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;

    public List<Transacao> obterTransacoesPorPeriodo(Date date) {
        // Obtém todas as transações normais no período
        List<Transacao> transacoesNormais = transacaoRepository.findAllByMonthYear(date.getYear(), date.getMonth()+1);

        // Obtém as transações fixas
        List<TransacaoFixa> transacoesFixas = transacaoFixaRepository.findAll();

        // Verifica e gera transações fixas para o período
        for (TransacaoFixa transacaoFixa : transacoesFixas) {
            gerarTransacoesFixasParaPeriodo(transacaoFixa, transacoesNormais);
        }

        return transacoesNormais;
    }

    private void gerarTransacoesFixasParaPeriodo(TransacaoFixa transacaoFixa, List<Transacao> transacoesExistentes) {

        LocalDate data = transacaoFixa.getDataCadastro();

        while (true) {
            // Verifica se já existe uma transação com esta data e descrição
            boolean jaExiste = transacoesExistentes.stream()
                    .anyMatch(t -> t.getDescricao().equals(transacaoFixa.getDescricao()));
                           // && t.getData().equals(dataAtual));

            if (!jaExiste) {
                // Adiciona uma nova transação à lista
                Transacao novaTransacao = new Transacao();
                novaTransacao.setDescricao(transacaoFixa.getDescricao());
                novaTransacao.setValor(transacaoFixa.getValor());
               // novaTransacao.setData(dataAtual);
                transacoesExistentes.add(novaTransacao);
            }

            // Avança para a próxima ocorrência com base na periodicidade
          //  dataAtual = avancarData(dataAtual, transacaoFixa.getPeriodicidade());
        }
    }

    private LocalDate avancarData(LocalDate data, Periodicidade periodicidade) {
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
