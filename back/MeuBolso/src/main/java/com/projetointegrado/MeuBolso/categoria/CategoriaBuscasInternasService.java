package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaBuscaInternaDTO;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaBuscasInternasService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoService transacaoService;

    @Transactional
    public List<CategoriaBuscaInternaDTO> buscaCategoriasInternas(String userId, LocalDate dataFinal) {
        LocalDate dataInicial = dataFinal.with(TemporalAdjusters.firstDayOfMonth());
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaBuscaInternaDTO> dtos = new ArrayList<>();

        for (Categoria categoria : categorias) {
            BigDecimal TotalGastosMensais = transacaoService.findSumDespesasInRangeByMonth(userId, dataFinal);
            BigDecimal totalGastosCategoria =  transacaoRepository.getSomatorioInRangeByCategoria(dataInicial, dataFinal, categoria.getId(), userId); //mudar para ficar no service de transacao
            BigDecimal prctGasto = totalGastosCategoria.multiply(new BigDecimal(100)).divide(TotalGastosMensais);
            dtos.add(new CategoriaBuscaInternaDTO(categoria, TotalGastosMensais, prctGasto));
        }
        return dtos;
    }
}
