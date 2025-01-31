package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaBuscaInternaDTO;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CategoriaBuscasInternasService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional
    public List<CategoriaBuscaInternaDTO> buscaCategoriasInternas(String userId, LocalDate dataFinal) {
        LocalDate dataInicial = dataFinal.with(TemporalAdjusters.firstDayOfMonth());
        List<Categoria> categorias = categoriaRepository.findAll();
        for (Categoria categoria : categorias) {
            BigDecimal somatorio =  transacaoRepository.getSomatorioInRangeByCategoria(dataInicial, dataFinal, categoria.getId(), userId);
        }

    }
}
