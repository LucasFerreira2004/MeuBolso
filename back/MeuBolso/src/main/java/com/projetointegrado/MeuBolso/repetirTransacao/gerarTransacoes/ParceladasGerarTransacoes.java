package com.projetointegrado.MeuBolso.repetirTransacao.gerarTransacoes;

import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacao.OrigemTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ParceladasGerarTransacoes implements IGerarTransacoesStrategy{
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    public void gerarTransacoes(TransacaoRecorrente transacaoRecorrente, LocalDate dataBusca) {
        //chama a factory de estragies de avanço e obtem a estratégia correta de acordo com a periodiciade da transação recorrente
        IAvancoDataStrategy AvancoStrategy = AvancoDataFactory.getStrategy(transacaoRecorrente.getPeriodicidade());
        LocalDate dataUltimaExecucao;
        if(transacaoRecorrente.getUltimaExecucao() != null){
            //executando a strategy
            dataUltimaExecucao = AvancoStrategy.avancarData(transacaoRecorrente.getUltimaExecucao(), transacaoRecorrente.getDataCadastro(), 1);
        }else{
            dataUltimaExecucao = transacaoRecorrente.getDataCadastro();
        }

        while (!dataUltimaExecucao.isAfter(dataBusca) && !dataUltimaExecucao.isAfter(transacaoRecorrente.getDataFinal())) {
            Transacao novaTransacao = new Transacao(transacaoRecorrente, dataUltimaExecucao, OrigemTransacao.PARCELADA);
            transacaoRepository.save(novaTransacao);
            transacaoRecorrente.setUltimaExecucao(dataUltimaExecucao);
            //executando a strategy
            dataUltimaExecucao = AvancoStrategy.avancarData(dataUltimaExecucao, transacaoRecorrente.getDataCadastro(), 1);
        }
        transacaoRecorrenteRepository.save(transacaoRecorrente);
        transacaoRecorrenteRepository.flush();
    }
}
