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
        IAvancoDataStrategy avancoMensal = AvancoDataFactory.getStrategy(Periodicidade.MENSAL);
        LocalDate dataAvanco = dataInicial.with(TemporalAdjusters.lastDayOfMonth());
        List<SaldoBalancoDTO> dtos = new ArrayList<>();

        while(!dataAvanco.isAfter(dataFinal)) {
            BigDecimal saldo = contaService.findSaldo(userId, dataAvanco).getSaldo();
            dtos.add(new SaldoBalancoDTO(dataAvanco.getYear(), dataAvanco.getMonthValue(), saldo));
        }
        return dtos;
    }
}
