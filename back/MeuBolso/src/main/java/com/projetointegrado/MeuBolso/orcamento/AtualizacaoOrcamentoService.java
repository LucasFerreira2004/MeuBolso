package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class AtualizacaoOrcamentoService {

    @Autowired
    OrcamentoRepository orcamentoRepository;

    @Autowired
    TransacaoRepository transacaoRepository;

    @Transactional
    public void atualizarOrcamentos(String usuarioId, Integer ano, Integer mes) {
        LocalDate periodo = LocalDate.of(ano, mes, 1);
        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioAndPeriodo(usuarioId, ano, mes);

        LocalDate dataInicio = periodo.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFim = periodo.with(TemporalAdjusters.lastDayOfMonth());

        orcamentos.forEach(orcamento -> {
            BigDecimal gastoTotal = transacaoRepository.calcularGastoPorCategoriaEPeriodo(
                    orcamento.getCategoria().getId(), usuarioId, dataInicio, dataFim
            );

            // Atualiza os valores de total gasto e restante
            orcamento.updateValores(gastoTotal);
//            System.out.println("Update orcamento: " + orcamento.getNotificacao().getThreshold());

            // Verifica se algum threshold (50%, 90%, 100%) foi atingido
            orcamento.verificarThresholds();
//            System.out.println("Update orcamento: " + orcamento.getNotificacao().getThreshold());

            orcamentoRepository.save(orcamento);
        });
    }
}
