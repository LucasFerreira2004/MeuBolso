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
        System.out.println("atualializarOrcamentos: buscando no banco de dados");
        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioAndPeriodo(usuarioId, periodo.getYear(), periodo.getMonth().getValue());

        System.out.println("atualializarOrcamentos: busca de dados -> AtualizarValores");
        orcamentos.forEach(orcamento -> {
            LocalDate dataInicio = periodo.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate dataFim = periodo.with(TemporalAdjusters.lastDayOfMonth());

            BigDecimal gastoTotal = transacaoRepository.calcularGastoPorCategoriaEPeriodo(
                    orcamento.getCategoria().getId(), usuarioId, dataInicio, dataFim
            );
            System.out.println("Gasto calculado: " + gastoTotal);

            orcamento.setValorGasto(gastoTotal);
            orcamento.setValorRestante(orcamento.getValorEstimado().subtract(gastoTotal));

            orcamentoRepository.save(orcamento);
            System.out.println("Orcamento: " + orcamento.getDescricao() + " - Gasto: R$ " + gastoTotal + " - Restante: R$ " + orcamento.getValorRestante());
        });
        System.out.println("atualializarOrcamentos: dados atualizados");
    }
}
