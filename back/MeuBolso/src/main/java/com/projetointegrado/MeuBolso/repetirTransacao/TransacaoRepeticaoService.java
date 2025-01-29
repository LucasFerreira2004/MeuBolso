package com.projetointegrado.MeuBolso.repetirTransacao;

import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoFixa;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoFixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        IAvancoDataStrategy AvancoStrategy = AvancoDataFactory.getStrategy(transacaoFixa.getPeriodicidade());
        LocalDate dataUltimaExecucao;
        if(transacaoFixa.getUltimaExecucao() != null){
            dataUltimaExecucao = AvancoStrategy.avancarData(transacaoFixa.getUltimaExecucao(), transacaoFixa.getDataCadastro(), 1);
        }else{
            dataUltimaExecucao = transacaoFixa.getDataCadastro();
        }

        while (!dataUltimaExecucao.isAfter(dataBusca)) {
            Transacao novaTransacao = new Transacao(transacaoFixa, dataUltimaExecucao);
            transacaoRepository.save(novaTransacao);
            transacaoFixa.setUltimaExecucao(dataUltimaExecucao);

            dataUltimaExecucao = AvancoStrategy.avancarData(dataUltimaExecucao, transacaoFixa.getDataCadastro(), 1);
        }
        System.out.println("TransacaoRepeticaoService -> gerarTransacoesFixas -> ultimaExecucao = " + transacaoFixa.getUltimaExecucao());
        transacaoFixaRepository.save(transacaoFixa);
    }


}
