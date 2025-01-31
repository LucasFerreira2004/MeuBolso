package com.projetointegrado.MeuBolso.dashBoards;

import com.projetointegrado.MeuBolso.dashBoards.dto.TransacaoBalancoDTO;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.TransacaoService;
import com.projetointegrado.MeuBolso.transacaoRecorrente.Periodicidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacoesDashboardsService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoService transacaoService;

    public List<TransacaoBalancoDTO> getTransacoesBalancos(String userId, LocalDate dataInicial, LocalDate dataFinal) {
        System.out.println("==============================> Gerar transacoes Balanco");
        IAvancoDataStrategy avancoMensal = AvancoDataFactory.getStrategy(Periodicidade.MENSAL);
        LocalDate dataAvanco = dataInicial.with(TemporalAdjusters.lastDayOfMonth());
        List<TransacaoBalancoDTO> dtos = new ArrayList<>();
        System.out.println("dataavanco: "+dataAvanco);
        System.out.println("dataFinal:" + dataFinal);
        while(!dataAvanco.isAfter(dataFinal)) {
            BigDecimal despesas = transacaoService.findSumDespesasInRangeByMonth(userId, dataAvanco);
            BigDecimal receitas  = transacaoService.findSumReceitasInRangeByMonth(userId, dataAvanco);
            dtos.add(new TransacaoBalancoDTO(dataAvanco.getYear(), dataAvanco.getMonthValue(), despesas, receitas));
            dataAvanco = avancoMensal.avancarData(dataAvanco, dataInicial, 1);
            System.out.println("dataavanco: "+dataAvanco);
        }
        return dtos;
    }

}
