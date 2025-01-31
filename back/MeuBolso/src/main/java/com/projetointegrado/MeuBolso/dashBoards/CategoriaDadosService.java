package com.projetointegrado.MeuBolso.dashBoards;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.categoria.ICategoriaService;
import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.dashBoards.dto.CategoriaDadosDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaDadosService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoService transacaoService;

    @Transactional
    public List<CategoriaDadosDTO> getDadosCategorias(String userId, LocalDate dataFinal, TipoTransacao tipo) {
        LocalDate dataInicial = dataFinal.with(TemporalAdjusters.firstDayOfMonth());
        List<Categoria> categorias = categoriaRepository.findAllNotInternByTipo(userId, TipoCategoria.valueOf(tipo.name()));
        List<CategoriaDadosDTO> dtos = new ArrayList<>();

        for (Categoria categoria : categorias) {
            BigDecimal totalGastosMensais = transacaoRepository.getSumInRangeByTipo(dataInicial, dataFinal, userId, tipo);
            BigDecimal gastosCategoria =  transacaoRepository.getSumInRangeByCategoria(dataInicial, dataFinal, categoria.getId(), userId); //mudar para ficar no service de transacao
            if (gastosCategoria == null || totalGastosMensais == null)
                continue;
            BigDecimal prctGasto = gastosCategoria
                    .multiply(new BigDecimal(100))
                    .divide(totalGastosMensais, 2, RoundingMode.HALF_UP);
            dtos.add(new CategoriaDadosDTO(categoria, totalGastosMensais, prctGasto));
        }
        return dtos;
    }
}
