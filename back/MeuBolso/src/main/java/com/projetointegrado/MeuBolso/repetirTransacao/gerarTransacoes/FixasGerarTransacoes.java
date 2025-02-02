package com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes;

import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FixasGerarTransacoes implements IGerarTransacoesStrategy{
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    @Override
    public void gerarTransacoes(TransacaoRecorrente transacaoRecorrente, LocalDate dataBusca) {
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
