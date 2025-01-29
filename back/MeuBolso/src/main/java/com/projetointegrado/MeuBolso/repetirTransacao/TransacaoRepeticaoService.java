package com.projetointegrado.MeuBolso.repetirTransacao;

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

        List<TransacaoRecorrente> transacoesFixas = transacaoRecorrenteRepository.findAllByUsuario(userId);
        if (transacoesNormais.isEmpty() || transacoesFixas.isEmpty()) return null;

        for (TransacaoRecorrente transacaoRecorrente : transacoesFixas) {
            gerarTransacoesFixas(transacaoRecorrente, data);
        }

        return transacoesNormais;
    }
    private void gerarTransacoesFixas(TransacaoRecorrente transacaoRecorrente, LocalDate dataBusca) {
        System.out.println("TransacaoRecorrenteService -> gerarTransacoesFixas");
        IAvancoDataStrategy AvancoStrategy = AvancoDataFactory.getStrategy(transacaoRecorrente.getPeriodicidade());
        LocalDate dataUltimaExecucao;
        if(transacaoRecorrente.getUltimaExecucao() != null){
            dataUltimaExecucao = AvancoStrategy.avancarData(transacaoRecorrente.getUltimaExecucao(), transacaoRecorrente.getDataCadastro(), 1);
        }else{
            dataUltimaExecucao = transacaoRecorrente.getDataCadastro();
        }

        while (!dataUltimaExecucao.isAfter(dataBusca)) {
            Transacao novaTransacao = new Transacao(transacaoRecorrente, dataUltimaExecucao);
            transacaoRepository.save(novaTransacao);
            transacaoRecorrente.setUltimaExecucao(dataUltimaExecucao);

            dataUltimaExecucao = AvancoStrategy.avancarData(dataUltimaExecucao, transacaoRecorrente.getDataCadastro(), 1);
        }
        System.out.println("TransacaoRecorrenteService -> gerarTransacoesFixas -> ultimaExecucao = " + transacaoRecorrente.getUltimaExecucao());
        transacaoRecorrenteRepository.save(transacaoRecorrente);
    }


}
