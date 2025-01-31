package com.projetointegrado.MeuBolso.dashboard;

import com.projetointegrado.MeuBolso.conta.ContaRepository;
import com.projetointegrado.MeuBolso.conta.ContaService;
import com.projetointegrado.MeuBolso.dashboard.dto.SaldoBalancoDTO;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.AvancoDataFactory;
import com.projetointegrado.MeuBolso.repetirTransacao.avancarData.IAvancoDataStrategy;
import com.projetointegrado.MeuBolso.transacaoRecorrente.Periodicidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaDashboardService {
    @Autowired
    private ContaService contaService;

    public List<SaldoBalancoDTO> getBalancoSaldos(String userId, LocalDate dataInicial, LocalDate dataFinal) {
        System.out.println("==============================> Gerar transacoes Balanco");
        IAvancoDataStrategy avancoMensal = AvancoDataFactory.getStrategy(Periodicidade.ULTIMO_DIA_MES);
        LocalDate dataAvanco = dataInicial.with(TemporalAdjusters.lastDayOfMonth());
        List<SaldoBalancoDTO> dtos = new ArrayList<>();
        System.out.println("dataavanco: "+dataAvanco);
        System.out.println("dataFinal:" + dataFinal);
        while(!dataAvanco.isAfter(dataFinal)) {
            BigDecimal saldo = contaService.findSaldo(userId, dataAvanco).getSaldo();
            dtos.add(new SaldoBalancoDTO(dataAvanco.getYear(), dataAvanco.getMonthValue(), saldo));
            System.out.println("dataavanco: "+dataAvanco);
            dataAvanco = avancoMensal.avancarData(dataAvanco, dataInicial, 1);
        }
        return dtos;
    }
}
