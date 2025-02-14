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
    public void atualizarOrcamentos(String usuarioId, LocalDate periodo) {
        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioAndPeriodo(usuarioId, periodo.getYear(), periodo.getMonth().getValue());

        orcamentos.forEach(orcamento -> {
            LocalDate dataInicio = periodo.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate dataFim = periodo.with(TemporalAdjusters.lastDayOfMonth());

            BigDecimal gastoTotal = transacaoRepository.calcularGastoPorCategoriaEPeriodo(
                    orcamento.getCategoria().getId(), usuarioId, dataInicio, dataFim
            );

            // Atualiza os valores de total gasto e restante
            orcamento.updateValores(gastoTotal);

            // Verifica se algum threshold (50%, 90%, 100%) foi atingido
            orcamento.verificarThresholds();

            orcamentoRepository.save(orcamento);
        });
    }
}
