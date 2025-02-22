package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.orcamento.iterator.OrcamentoAgregado;
import com.projetointegrado.MeuBolso.orcamento.iterator.OrcamentoAgregado;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.orcamento.iterator.Iterator;
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
        LocalDate dataInicio = periodo.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFim = periodo.with(TemporalAdjusters.lastDayOfMonth());

        List<Orcamento> orcamentosList = orcamentoRepository.findByUsuarioAndPeriodo(usuarioId, ano, mes);
        OrcamentoAgregado orcamentoAggregate = new OrcamentoAgregado(orcamentosList);

        Iterator<Orcamento> it = orcamentoAggregate.iterator();
        while (it.hasNext()) {
            Orcamento orcamento = it.next();

            BigDecimal gastoTotal = transacaoRepository.calcularGastoPorCategoriaEPeriodo(
                    orcamento.getCategoria().getId(), usuarioId, dataInicio, dataFim);

            orcamento.updateValores(gastoTotal);
            orcamento.verificarThresholds();
            orcamentoRepository.save(orcamento);
        }
    }

}
